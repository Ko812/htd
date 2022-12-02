package com.ncs.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

public class DBOps {

	Connection con;
	ResultSet res;
	PreparedStatement pstmt;
	Statement stm;
	
	public DBOps() {
		super();
		try {
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/job_portal", "java_developer", "Password123!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public DBOps(Connection con) {
		super();
		this.con = con;
	}
	
	private void _connectIfNec() throws SQLException {
		if(con == null || con.isClosed()) {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/job_portal", "java_developer", "Password123!");
		}
	}
	
	private boolean validSessionCode(String email, String sc) {
		try {
			_connectIfNec();
			String s = "select code_expiration_date, code_expiration_time from session_codes where user_email=? AND session=?";
			pstmt = con.prepareStatement(s);
			pstmt.setString(1, email);
			pstmt.setString(2, sc);
			res = pstmt.executeQuery();
			if(res.next()) {
				LocalDate expDate = res.getDate(1).toLocalDate();
				LocalTime expTime = res.getTime(2).toLocalTime();
				LocalDateTime now = LocalDateTime.now();
				LocalDateTime expDateTime = LocalDateTime.of(expDate.getYear(), expDate.getMonth(), expDate.getDayOfMonth(), expTime.getHour(), expTime.getMinute(),expTime.getSecond());
				return now.isBefore(expDateTime);
			}
			else {
				return false;
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/********************************************
	 *  General DB ops
	 ********************************************/
	
	public boolean saveSessionCode(String sc, String email) {
		try {
			_connectIfNec();
			String s = "insert into session_codes (code_creation_date, code_creation_time, code_expiration_date, code_expiration_time, session, user_email) values (?, ?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(s);
			LocalDateTime now = LocalDateTime.now();
			LocalDateTime exp = now.plus(Duration.ofMinutes(5));
			LocalDate nowDate = LocalDate.of(now.getYear(), now.getMonth(), now.getDayOfMonth());
			LocalTime nowTime = LocalTime.of(now.getHour(), now.getMinute());
			LocalDate expDate = LocalDate.of(exp.getYear(), exp.getMonth(), exp.getDayOfMonth());
			LocalTime expTime = LocalTime.of(exp.getHour(), exp.getMinute());
			pstmt.setDate(1, Date.valueOf(nowDate));
			pstmt.setTime(2, Time.valueOf(nowTime));
			pstmt.setDate(3, Date.valueOf(expDate));
			pstmt.setTime(4, Time.valueOf(expTime));
			pstmt.setString(5, sc);
			pstmt.setString(6, email);
			return pstmt.executeUpdate()==1;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean verifyEmailExists(String email, String loginAs, HttpSession sess) {
		if(loginAs.equals("employer")) {
			return verifyEMEmailExists(email, sess);
		}
		else if(loginAs.equals("jobSeeker")) {
			return verifyJSEmailExists(email, sess);
		}
		return false;
	}
	
	public boolean resetPassword(String password, String loginAs, String email, String sc, HttpSession sess) {
		if(loginAs.equals("employer")) {
			return resetEMPassword(email, password, sc, sess);
		}
		else if(loginAs.equals("jobSeeker")) {
			return resetJSPassword(email, password, sc, sess);
		}
		return false;
	}
	
	/********************************************
	 *  Employer Class DB ops
	 ********************************************/
	
	public EmployerCompany loginEmployer(String userName, String password, HttpSession sess) {
		try {
			_connectIfNec();
			String v = "select * from employers where user_name=?";
			pstmt = con.prepareStatement(v);
			pstmt.setString(1, userName.toUpperCase());
			res = pstmt.executeQuery();
			if(res.next()) {
				if(res.getString(4).equals(Encryption.encrypt(password))) {
					EmployerCompany ec = new EmployerCompany();
					ec.setId(res.getInt(1));
					ec.setCompanyName(res.getString(2));
					ec.setUserName(res.getString(3));
					ec.setCompanyEmail(res.getString(5));
					ec.setCompanyDetails(res.getString(6));
					ec.setYearsOfOperation(res.getInt(7));
					ec.setVmv(res.getString(8));
					ec.setCompanyWebsite(res.getString(9));
					ec.setCompanySize(res.getInt(10));
					return ec;
				}
				else {
					sess.setAttribute("form-message-read", Boolean.parseBoolean("false"));
					sess.setAttribute("login-form-message", "Password incorrect.");
					return null;
				}
			}
			else {
				sess.setAttribute("form-message-read", Boolean.parseBoolean("false"));
				sess.setAttribute("login-form-message", "User not found.");
				return null;
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean verifyEMEmailExists(String email, HttpSession sess) {
		try {
			_connectIfNec();
			
			String s = "select user_name from employers where company_email=?";
			
			pstmt = con.prepareStatement(s);
			pstmt.setString(1, email.toUpperCase());
			res = pstmt.executeQuery();
			if(res.next()) {
				sess.setAttribute("reset-for-email", email);
				sess.setAttribute("loginAs", "employer");
				return true;
			}
			return false;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	boolean verifyEMUsernameExist(String userName) {
		try {
			_connectIfNec();
			
			String s = "select * from employers where user_name=?";
			pstmt = con.prepareStatement(s);
			pstmt.setString(1, userName.toUpperCase());
			res = pstmt.executeQuery();
			return res.next();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean createEmployer(EmployerCompany ec, String password, HttpSession sess) {
		try {
			_connectIfNec();
			if(!verifyEMUsernameExist(ec.getUserName())) {
				if(!verifyEMEmailExists(ec.getCompanyEmail(), sess)) {
					String s = "insert into employers (company_name, user_name, password, company_email) values (?, ?, ?, ?)";
					pstmt = con.prepareStatement(s);
					pstmt.setString(1, ec.getCompanyName().toUpperCase());
					pstmt.setString(2, ec.getUserName().toUpperCase());
					pstmt.setString(3, Encryption.encrypt(password));
					pstmt.setString(4, ec.getCompanyEmail().toUpperCase());
					int out = pstmt.executeUpdate();
					if(out == 1) {
						sess.setAttribute("form-message-read", Boolean.parseBoolean("false"));
						sess.setAttribute("login-form-message", "Sign up successful! Sign in to continue.");
						return true;
					}
					else {
						sess.setAttribute("form-message-read", Boolean.parseBoolean("false"));
						sess.setAttribute("register-employer-form-message", "Sign up unsuccessful. Try again later.");
						return false;
					}
				}
				else {
					sess.setAttribute("form-message-read", Boolean.parseBoolean("false"));
					sess.setAttribute("register-employer-form-message", "Email is already used.");
					return false;
				}
			}
			else {
				sess.setAttribute("form-message-read", Boolean.parseBoolean("false"));
				sess.setAttribute("register-employer-form-message", "User name is already used.");
				return false;
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean resetEMPassword(String email, String password, String sessionCode, HttpSession sess) {
		try {
			_connectIfNec();
			if(validSessionCode(email, sessionCode)) {
				String s = "update employers set password=? where company_email=?";
				pstmt = con.prepareStatement(s);
				pstmt.setString(1, Encryption.encrypt(password));
				pstmt.setString(2, email);
				return pstmt.executeUpdate() == 1;
			}
			else {
				sess.setAttribute("login-form-message", "Your password reset link is no longer valid. Please try again.");
				sess.setAttribute("form-message-read", Boolean.parseBoolean("false"));
				return false;
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Integer updateEmployer(EmployerCompany ec) {
		try {
			_connectIfNec();
			
			String s = "update employers set company_name=?, user_name=?, company_email=?, company_details=?, company_years_of_operation=?, vmv=?, company_website=?, company_size=? where id=?";
			
			pstmt = con.prepareStatement(s);
			pstmt.setString(1, ec.getCompanyName().toUpperCase());
			pstmt.setString(2, ec.getUserName().toUpperCase());
			pstmt.setString(3, ec.getCompanyEmail().toUpperCase());
			pstmt.setString(4, ec.getCompanyDetails());
			pstmt.setInt(5, ec.getYearsOfOperation());
			pstmt.setString(6, ec.getVmv());
			pstmt.setString(7, ec.getCompanyWebsite());
			pstmt.setInt(8, ec.getCompanySizeInt());
			pstmt.setInt(9, ec.getId());
			return pstmt.executeUpdate();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean deleteEMAccount(EmployerCompany ec) {
		try {
			_connectIfNec();
			
			String s = "delete from employers where id=?";
			pstmt = con.prepareStatement(s);
			pstmt.setInt(1, ec.getId());
			int out = pstmt.executeUpdate();
			if(out == 1) {
				return true;
			}
			return false;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/********************************************
	 *  Job DB ops
	 ********************************************/
	
	public Integer createJob(Job job) {
		try {
			_connectIfNec();
			
			String s = "insert into jobs (date_posted, job_role, job_desc, salary, years_of_exp, employer_id, company_name, company_email, job_status) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			Date date = Date.valueOf(LocalDate.now());
			pstmt = con.prepareStatement(s);
			pstmt.setDate(1, date);
			pstmt.setString(2, job.getRole().toUpperCase());
			pstmt.setString(3, job.getDesc());
			pstmt.setInt(4, job.getSalary());
			pstmt.setInt(5, job.getYears_of_exp());
			pstmt.setInt(6, job.getEmployer_id());
			pstmt.setString(7, job.getCompany_name());
			pstmt.setString(8, job.getCompany_email());
			pstmt.setString(9, job.getStatus().toString());
			return pstmt.executeUpdate();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public Integer editJob(Job job, HttpSession sess) {
		try {
			_connectIfNec();
			if(!jobPostHasApplicants(job.getId())) {
				String s = "update jobs set job_role=?, job_desc=?, salary=?, years_of_exp=? where id=?";
				pstmt = con.prepareStatement(s);
				pstmt.setString(1, job.getRole().toUpperCase());
				pstmt.setString(2, job.getDesc());
				pstmt.setInt(3, job.getSalary());
				pstmt.setInt(4, job.getYears_of_exp());
				pstmt.setInt(5, job.getId());
				return pstmt.executeUpdate();
			}
			else {
				sess.setAttribute("outcome", "Job posting already has applicants. To change job details, delete and create a new job posting.");
				sess.setAttribute("outcome-read", Boolean.parseBoolean("false"));
				return -1;
			}
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public boolean jobPostHasApplicants(int jobID) {
		try {
			_connectIfNec();
			String s = "select id from applications where job_id=?";
			pstmt = con.prepareStatement(s);
			pstmt.setInt(1, jobID);
			res = pstmt.executeQuery();
			return res.next();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Integer closeJob(Job job) {
		try {
			_connectIfNec();
			
			String a = "select id from applications where job_id=?";
			pstmt = con.prepareStatement(a);
			
			// Query all applications for the job posting.
			pstmt.setInt(1, job.getId());
			res = pstmt.executeQuery();
			List<Integer> appsToUpdate = new ArrayList<Integer>();
			while (res.next()) {
				int appID = res.getInt(1);
				appsToUpdate.add(appID);
			}

			// Update all applications for the job posting.
			String d = "update applications set application_status=? where ";
			
			for (int i = 0; i < appsToUpdate.size(); i++) {
				d = d.concat("id=? OR ");
			}
			d = d.concat("false");
			pstmt = con.prepareStatement(d);
			pstmt.setString(1, ApplicationStatus.JobClosed.toString());
			for (int i = 0; i < appsToUpdate.size(); i++) {
				pstmt.setInt(i + 2, appsToUpdate.get(i));
			}
			int out = pstmt.executeUpdate();
			if (out == appsToUpdate.size()) {
				String cj = "update jobs set job_status=? where id=?";
				pstmt = con.prepareStatement(cj);
				pstmt.setString(1, JobStatus.Closed.toString());
				pstmt.setInt(2, job.getId());
				return pstmt.executeUpdate();
			} else {
				return -1;
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public Integer closeJob(int jobID) {
		try {
			_connectIfNec();
			String a = "select id from applications where job_id=?";
			pstmt = con.prepareStatement(a);
			
			// Query all applications for the job posting.
			pstmt.setInt(1, jobID);
			res = pstmt.executeQuery();
			List<Integer> appsToUpdate = new ArrayList<Integer>();
			while(res.next()) {
				int appID = res.getInt(1);
				appsToUpdate.add(appID);
			}
			
			// Update all applications for the job posting.
			String u = "update applications set application_status=? where ";
			
			for(int i=0;i<appsToUpdate.size();i++) {
				u = u.concat("id=? || ");
			}
			u = u.concat("false");
			pstmt = con.prepareStatement(u);
			pstmt.setString(1, ApplicationStatus.JobClosed.toString());
			for(int i = 0; i < appsToUpdate.size(); i++) {
				pstmt.setInt(i + 2, appsToUpdate.get(i));
			}
			int out = pstmt.executeUpdate();
			if(out == appsToUpdate.size()) {
				String s = "update jobs set job_status=? where id=?";
				pstmt = con.prepareStatement(s);
				pstmt.setString(1, JobStatus.Closed.toString());
				pstmt.setInt(2, jobID);
				return pstmt.executeUpdate();
			}
			else {
				return -1;
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public List<Job> getAllJobPostings(int employer_id) {
		try {
			_connectIfNec();
			ArrayList<Job> jobPostings = new ArrayList<Job>();
			String s = "select * from jobs where employer_id=?";
			pstmt = con.prepareStatement(s);
			pstmt.setInt(1, employer_id);
			res = pstmt.executeQuery();
			while(res.next()) {
				Job job = new Job(
						res.getString(3),
						res.getString(4),
						res.getInt(5),
						res.getInt(6), this,
						res.getString(8),
						res.getString(9)
						);
				job.setId(res.getInt(1));
				job.setDatePosted(res.getDate(2));
				job.setEmployer_id(employer_id);
				job.setStatus(JobStatus.valueOf(res.getString(10)));
				if(job.getStatus() == JobStatus.Open) {
					jobPostings.add(job);
				}
			}
			return jobPostings;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<JobSeeker> getAllJobApplicants(Job job){
		ArrayList<JobSeeker> applicants = new ArrayList<JobSeeker>();
		try {
			_connectIfNec();
			String s = "select * from applications where job_id=?";
			
			pstmt = con.prepareStatement(s);
			pstmt.setInt(1, job.getId());
			res =  pstmt.executeQuery();
			while(res.next()) {
				int jsID = res.getInt(5);
				JobSeeker js = new JobSeeker(this);
				js.setId(jsID);
				applicants.add(js);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return applicants;
	}
	
	public List<Job> getAllJobApplicantsForAllJobs(List<Job> jobs){
		for(Job job : jobs) {
			ArrayList<JobSeeker> applicants = new ArrayList<JobSeeker>();
			try {
				_connectIfNec();
				String s = "select application_status, job_seeker_id from applications where job_id=?";
				pstmt = con.prepareStatement(s);
				pstmt.setInt(1, job.getId());
				res =  pstmt.executeQuery();
				while(res.next()) {
					ApplicationStatus appStatus = ApplicationStatus.valueOf(res.getString(1));
					int jsID = res.getInt(2);
					if(appStatus != ApplicationStatus.JobClosed) {
						JobSeeker js = new JobSeeker(this);
						js.setId(jsID);
						applicants.add(js);
					}
				}
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
			job.setApplicants(applicants);
		}
		return jobs;
	}
	
	public List<Job> searchByJobRole(List<String> textPatterns){
		List<Job> jobs = new ArrayList<Job>();
		try {
			_connectIfNec();
			for(int i=0; i<textPatterns.size();i++) {
				String s = "select * from jobs where job_role like ? limit 0, 10";
				pstmt = con.prepareStatement(s);
				pstmt.setString(1, textPatterns.get(i));
				res = pstmt.executeQuery();
				while(res.next()) {
					Job job = new Job(res.getString(3), res.getString(4), res.getInt(5), res.getInt(6), this, res.getString(8), res.getString(9));
					job.setId(res.getInt(1));
					job.setDatePosted(res.getDate(2));
					job.setStatus(JobStatus.valueOf(res.getString(10)));
					if(job.getStatus() == JobStatus.Open) {
						jobs.add(job);
					}
				}
			}
			return jobs;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return jobs;
	}
	
	public List<Job> searchBySalary(int sal){
		List<Job> jobs = new ArrayList<Job>();
		try {
			_connectIfNec();
			String s = "select * from jobs where salary>=? AND salary<=? limit 0, 10";
			pstmt = con.prepareStatement(s);
			pstmt.setInt(1, sal - 1000);
			pstmt.setInt(2, sal + 1000);
			res = pstmt.executeQuery();
			while(res.next()) {
				Job job = new Job(res.getString(3), res.getString(4), res.getInt(5), res.getInt(6), this, res.getString(8), res.getString(9));
				job.setId(res.getInt(1));
				job.setDatePosted(res.getDate(2));
				job.setStatus(JobStatus.valueOf(res.getString(10)));
				if(job.getStatus() == JobStatus.Open) {
					jobs.add(job);
				}
				
			}
			return jobs;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return jobs;
	}
	
	public List<Job> searchByCompanyName(List<String> textPatterns){
		List<Job> jobs = new ArrayList<Job>();
		try {
			_connectIfNec();
			for(int i=0; i<textPatterns.size();i++) {
				String s = "select * from jobs where company_name like ? limit 0, 20";
				pstmt = con.prepareStatement(s);
				pstmt.setString(1, textPatterns.get(i));
				res = pstmt.executeQuery();
				while(res.next() && jobs.size() < 20) {
					Job job = new Job(res.getString(3), res.getString(4), res.getInt(5), res.getInt(6), this, res.getString(8), res.getString(9));
					job.setId(res.getInt(1));
					job.setDatePosted(res.getDate(2));
					job.setStatus(JobStatus.valueOf(res.getString(10)));
					if(job.getStatus() == JobStatus.Open) {
						jobs.add(job);
					}
				}
			}
			return jobs;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return jobs;
	}
	
	public List<Job> searchByYOE(int yoe){
		List<Job> jobs = new ArrayList<Job>();
		try {
			_connectIfNec();
			String s = "select * from jobs where years_of_exp >= ? AND years_of_exp<=? limit 0,10";
			pstmt = con.prepareStatement(s);
			pstmt.setInt(1, yoe - 2);
			pstmt.setInt(2, yoe + 2);
			res = pstmt.executeQuery();
			while(res.next()) {
				Job job = new Job(res.getString(3), res.getString(4), res.getInt(5), res.getInt(6), this, res.getString(8), res.getString(9));
				job.setId(res.getInt(1));
				job.setDatePosted(res.getDate(2));
				job.setStatus(JobStatus.valueOf(res.getString(10)));
				if(job.getStatus() == JobStatus.Open) {
					jobs.add(job);
				}
			}
			return jobs;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return jobs;
	}
	
	public List<Job> loadAllJobs(){
		List<Job> jobs = new ArrayList<Job>();
		try {
			_connectIfNec();
			String s = "select * from jobs ORDER BY date_posted DESC";
			pstmt = con.prepareStatement(s);
			res = pstmt.executeQuery();
			while(res.next()) {
				Job job = new Job(res.getString(3), res.getString(4), res.getInt(5), res.getInt(6), this, res.getString(8), res.getString(9));
				job.setId(res.getInt(1));
				job.setDatePosted(res.getDate(2));
				job.setStatus(JobStatus.valueOf(res.getString(10)));
				if(job.getStatus() == JobStatus.Open) {
					jobs.add(job);
				}
			}
			return jobs;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return jobs;
	}
	
	/********************************************
	 *  Job Applications DB ops
	 ********************************************/
	
	public boolean createJobApplication(JobApplication app) {
		try {
			_connectIfNec();
			int totalCred = app.getApplicationCredential().size();
			String s = "insert into applications ("
					+ "company_name, "//1
					+ "company_email, "//2
					+ "job_role, "//3
					+ "job_desc, "//4
					+ "salary, "//5
					+ "years_of_exp, "//6
					+ "application_date, "//7
					+ "application_status, "//8
					+ "js_first_name, "//9
					+ "js_last_name, "//10
					+ "js_contact, "//11
					+ "js_email, "//12
					+ "js_years_of_exp, "//13
					+ "js_identification_number, ";//14
			String qmark = "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ";
			if(totalCred > 0) {
				if(totalCred > 0) {
					for(int i = 1; i <= totalCred;i++) {
						s = s.concat("js_cred_"+i+", ");
						qmark = qmark.concat("?, ");
					}
				}
			}
			s = s.concat("job_id, job_seeker_id) " + qmark + "?, ?)");
			Date date = Date.valueOf(LocalDate.now());
			pstmt = con.prepareStatement(s, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, app.getCompanyName());
			pstmt.setString(2, app.getCompanyEmail());
			pstmt.setString(3, app.getJobRole());
			pstmt.setString(4, app.getJobDesc());
			pstmt.setInt(5, app.getSalary());
			pstmt.setInt(6, app.getYearsOfExp());
			pstmt.setDate(7, date);
			pstmt.setString(8, app.getStatus().toString());
			pstmt.setString(9, app.getJsFirstName());
			pstmt.setString(10, app.getJsLastName());
			pstmt.setString(11, app.getJsContact());
			pstmt.setString(12, app.getJsEmail());
			pstmt.setInt(13, app.getJsYearsOfExp());
			pstmt.setString(14, app.getJsIdentificationNumber());
			List<String> appCreds = app.getApplicationCredential();
			for(int i = 15; i <= 14 + totalCred;i++) {
				pstmt.setString(i, appCreds.get(i - 15));
			}
			pstmt.setInt(15 + totalCred, app.getJob_id());
			pstmt.setInt(16 + totalCred, app.getJob_seeker_id());
			if(pstmt.executeUpdate() == 1) {
				res = pstmt.getGeneratedKeys();
				if(res.next()) {
					String updateSent = "update applications set application_status=? where id=?";
					pstmt = con.prepareStatement(updateSent);
					pstmt.setString(1, ApplicationStatus.Sent.toString());
					// To get auto-generated key
					pstmt.setInt(2, res.getInt(1));
					pstmt.executeUpdate();
					app.setStatus(ApplicationStatus.Sent);
				}
				return true;
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<JobApplication> getAllApplications(int employer_id){
		ArrayList<JobApplication> applications = new ArrayList<JobApplication>();
		try {
			_connectIfNec();
			
			String s = "select * from applications where job_id in (select id from jobs where employer_id=?) ORDER BY application_date DESC";
			pstmt = con.prepareStatement(s);
			pstmt.setInt(1, employer_id);
			ResultSet res2 = pstmt.executeQuery();
			while(res2.next()) {
				JobApplication app = new JobApplication();
				app.setId(res2.getInt(1));
				app.setCompanyName(res2.getString(2));
				app.setCompanyEmail(res2.getString(3));
				app.setJobRole(res2.getString(4));
				app.setJobDesc(res2.getString(5));
				app.setSalary(res2.getInt(6));
				app.setYearsOfExp(res2.getInt(7));
				app.setApplicationDate(res2.getDate(8));
				app.setStatus(ApplicationStatus.valueOf(res2.getString(9)));
				app.setJsFirstName(res2.getString(10));
				app.setJsLastName(res2.getString(11));
				app.setJsContact(res2.getString(12));
				app.setJsEmail(res2.getString(13));
				app.setJsYearsOfExp(res2.getInt(14));
				app.setJsIdentificationNumber(res2.getString(15));
				int i = 16;
				List<String> appCreds = new ArrayList<String>();
				while(res2.getString(i) != null) {
					appCreds.add(res2.getString(i));
					i++;
				}
				app.setApplicationCredential(appCreds);
				app.setJob_id(res2.getInt(31));
				app.setJob_seeker_id(res2.getInt(32));
				if(app.getStatus() != ApplicationStatus.JobClosed){
					applications.add(app);
				}
			}
			return applications;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return applications;
	}
	
	public List<JobApplication> getJobApplications(int jobID){
		ArrayList<JobApplication> applications = new ArrayList<JobApplication>();
		try {
			_connectIfNec();
			
			String s = "select * from applications where job_id=?";
			pstmt = con.prepareStatement(s);
			pstmt.setInt(1, jobID);
			res = pstmt.executeQuery();
			while(res.next()) {
				JobApplication app = new JobApplication();
				app.setId(res.getInt(1));
				app.setCompanyName(res.getString(2));
				app.setCompanyEmail(res.getString(3));
				app.setJobRole(res.getString(4));
				app.setJobDesc(res.getString(5));
				app.setSalary(res.getInt(6));
				app.setYearsOfExp(res.getInt(7));
				app.setApplicationDate(res.getDate(8));
				app.setStatus(ApplicationStatus.valueOf(res.getString(9)));
				app.setJsFirstName(res.getString(10));
				app.setJsLastName(res.getString(11));
				app.setJsContact(res.getString(12));
				app.setJsEmail(res.getString(13));
				app.setJsYearsOfExp(res.getInt(14));
				app.setJsIdentificationNumber(res.getString(15));
				int i = 16;
				List<String> appCreds = new ArrayList<String>();
				while(res.getString(i) != null) {
					appCreds.add(res.getString(i));
					i++;
				}
				app.setApplicationCredential(appCreds);
				app.setJob_id(res.getInt(31));
				app.setJob_seeker_id(res.getInt(32));
				applications.add(app);
			}
			return applications;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return applications;
	}
	
	public List<JobApplication> pullJSJobsApplied(int jsID){
		ArrayList<JobApplication> applications = new ArrayList<JobApplication>();
		try {
			_connectIfNec();
			String s = "select * from applications where job_seeker_id=?";
			pstmt = con.prepareStatement(s);
			pstmt.setInt(1, jsID);
			res = pstmt.executeQuery();
			while(res.next()) {
				JobApplication app = new JobApplication();
				app.setId(res.getInt(1));
				app.setCompanyName(res.getString(2));
				app.setCompanyEmail(res.getString(3));
				app.setJobRole(res.getString(4));
				app.setJobDesc(res.getString(5));
				app.setSalary(res.getInt(6));
				app.setYearsOfExp(res.getInt(7));
				app.setApplicationDate(res.getDate(8));
				app.setStatus(ApplicationStatus.valueOf(res.getString(9)));
				app.setJsFirstName(res.getString(10));
				app.setJsLastName(res.getString(11));
				app.setJsContact(res.getString(12));
				app.setJsEmail(res.getString(13));
				app.setJsYearsOfExp(res.getInt(14));
				app.setJsIdentificationNumber(res.getString(15));
				int i = 16;
				List<String> appCreds = new ArrayList<String>();
				while(res.getString(i) != null) {
					appCreds.add(res.getString(i));
					i++;
				}
				app.setApplicationCredential(appCreds);
				app.setJob_id(res.getInt(31));
				app.setJob_seeker_id(res.getInt(32));
				if(app.getStatus() != ApplicationStatus.ApplicationRetracted) {
					applications.add(app);
				}
			}
			return applications;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return applications;
	}
	
	public boolean editJobApplication(JobApplication app, HttpSession sess) {
		try {
			_connectIfNec();
			int totalCred = app.getApplicationCredential().size();
			String s = "update applications set ";
					
			String condition = "where id=?";
			if(totalCred > 0) {
				for(int i = 1; i < totalCred;i++) {
					s = s.concat("js_cred_"+i+"=?, ");
				}
				s = s.concat("js_cred_"+totalCred+"=? ");
			}
			s = s.concat(condition);
			pstmt = con.prepareStatement(s);
			
			List<String> appCreds = app.getApplicationCredential();
			for(int i = 1; i <= totalCred;i++) {
				pstmt.setString(i, appCreds.get(i - 1));
			}
			pstmt.setInt(1 + totalCred, app.getId());
			return pstmt.executeUpdate() == 1;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean updateAppStatus(int appID, String updateStatus) {
		try {
			_connectIfNec();
			String s = "update applications set application_status=? where id=?";
			pstmt = con.prepareStatement(s);
			pstmt.setString(1, updateStatus);
			pstmt.setInt(2, appID);
			return pstmt.executeUpdate() == 1;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean updateSelectedAppsStatusTo(ApplicationStatus appStatus, List<JobApplication> apps) {
		try {
			_connectIfNec();
			String s = "update applications set application_status=? where ";
			if(!apps.isEmpty()) {
				s = s.concat("id=? ");
				for(int i = 1; i <apps.size(); i++) {
					s = s.concat("OR id=? ");
				}
			}
			pstmt = con.prepareStatement(s);
			pstmt.setString(1, appStatus.toString());
			for(int i = 0; i <apps.size(); i++) {
				pstmt.setInt(2 + i, apps.get(i).getId());
			}
			return pstmt.executeUpdate() == apps.size();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean batchUpdateApplicationStatus(List<JobApplication> apps) {
		try {
			_connectIfNec();
			int outcome = 0;
			for(JobApplication app: apps) {
				String s = "update applications set application_status=? where id=?";
				pstmt = con.prepareStatement(s);
				pstmt.setString(1, app.getStatus().toString());
				pstmt.setInt(2, app.getId());
				outcome += pstmt.executeUpdate();
			}
			return outcome == apps.size();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void deleteAllJobApplications(int jsID) {
		try {
			_connectIfNec();
			String s = "update applications set application_status=? where job_seeker_id=?";
			pstmt = con.prepareStatement(s);
			pstmt.setString(1, ApplicationStatus.ApplicationRetracted.toString());
			pstmt.setInt(2, jsID);
			pstmt.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/********************************************
	 *  JobSeeker Class DB ops
	 ********************************************/
	
	public JobSeeker loginJobSeeker(String userName, String password, HttpSession sess) {
		try {
			_connectIfNec();
			String v = "select * from job_seekers where user_name=?";
			
			pstmt = con.prepareStatement(v);
			pstmt.setString(1, userName.toUpperCase());
			res = pstmt.executeQuery();
			if(res.next()) {
				if(res.getString(5).equals(Encryption.encrypt(password))) {
					JobSeeker js = new JobSeeker();
					js.setId(res.getInt(1));
					js.setFirstName(res.getString(2));
					js.setLastName(res.getString(3));
					js.setUserName(res.getString(4));
					js.setEmail(res.getString(6));
					js.setNRIC(res.getString(7));
					js.setContact(res.getString(8));
					for(int i = 0; i < 5; i++) {
						String acad = res.getString(9 + i);
						if(acad != null) {
							js.addAcad(acad);
						}
					}
					js.setYearsOfExperience(res.getInt(14));
					for(int i = 0; i < 5; i++) {
						String workExp = res.getString(16 + i);
						if(workExp != null) {
							js.addWorkExp(workExp);
						}
					}
//					js.setPersonalWebsite(res.getString(15));
					for(int i = 0; i < 5; i++) {
						String award = res.getString(21 + i);
						if(award != null) {
							js.addAward(award);
						}
					}
					return js;
				}
				else {
					sess.setAttribute("form-message-read", Boolean.parseBoolean("false"));
					sess.setAttribute("login-form-message", "Password incorrect.");
				}
			}
			else {
				sess.setAttribute("form-message-read", Boolean.parseBoolean("false"));
				sess.setAttribute("login-form-message", "Invalid user name and password.");
			}
		}
		catch(SQLException e) {
			sess.setAttribute("form-message-read", Boolean.parseBoolean("false"));
			sess.setAttribute("login-form-message", "User not found.");
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	public boolean createJobSeeker(JobSeeker js, String password, HttpSession sess) {
		try {
			_connectIfNec();
			if(!verifyJSUsernameExist(js.getUserName())) {
				if(!verifyJSEmailExists(js.getEmail(), sess)) {
					String v = "insert into job_seekers (first_name, last_name, user_name, password, email, NRIC, contact) values (?, ?, ?, ?, ?, ?, ?)";
					
					pstmt = con.prepareStatement(v);
					pstmt.setString(1, js.getFirstName().toUpperCase());
					pstmt.setString(2, js.getLastName().toUpperCase());
					pstmt.setString(3, js.getUserName().toUpperCase());
					pstmt.setString(4, Encryption.encrypt(password));
					pstmt.setString(5, js.getEmail().toUpperCase());
					pstmt.setString(6, js.getNRIC().toUpperCase());
					pstmt.setString(7, js.getContact());
					int out = pstmt.executeUpdate();
					if(out == 1) {
						sess.setAttribute("form-message-read", Boolean.parseBoolean("false"));
						sess.setAttribute("login-form-message", "Sign up successful! Sign in to continue.");
						return true;
					}
					else {
						sess.setAttribute("form-message-read", Boolean.parseBoolean("false"));
						sess.setAttribute("register-job-seeker-form-message", "Sign up failed. Try again later.");
						return false;
					}
				}
				else {
					sess.setAttribute("form-message-read", Boolean.parseBoolean("false"));
					sess.setAttribute("register-job-seeker-form-message", "Email is already used.");
					return false;
				}
			}
			else {
				sess.setAttribute("form-message-read", Boolean.parseBoolean("false"));
				sess.setAttribute("register-job-seeker-form-message", "User name is already used.");
				return false;
			}
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	boolean verifyJSUsernameExist(String userName) {
		try {
			_connectIfNec();
			
			String s = "select * from job_seekers where user_name=?";
			pstmt = con.prepareStatement(s);
			pstmt.setString(1, userName.toUpperCase());
			res = pstmt.executeQuery();
			return res.next();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean resetJSPassword(String email, String password, String sc, HttpSession sess) {
		try {
			_connectIfNec();
			
			if(validSessionCode(email, sc)) {
				String s = "update job_seekers set password=? where email=?";
				pstmt = con.prepareStatement(s);
				pstmt.setString(1, Encryption.encrypt(password));
				pstmt.setString(2, email);
				return pstmt.executeUpdate() == 1;
			}
			else {
				sess.setAttribute("login-form-message", "Password reset link is no longer valid. Please try again.");
				sess.setAttribute("form-message-read", Boolean.parseBoolean("false"));
				return false;
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean changePassword(JobSeeker js, String oldPassword, String newPassword, HttpSession sess) {
		try {
			_connectIfNec();
			
			String s = "select * from job_seekers where user_name=?";
			
			pstmt = con.prepareStatement(s);
			pstmt.setString(1, js.getUserName());
			
			res = pstmt.executeQuery();
			if(res.next()) {
				if(res.getString(5).equals(Encryption.encrypt(oldPassword))) {
					String s1 = "update job_seekers set password=? where id=?";
					pstmt = con.prepareStatement(s1);
					pstmt.setString(1, Encryption.encrypt(newPassword));
					pstmt.setInt(2, res.getInt(1));
					int outcome = pstmt.executeUpdate();
					if(outcome == 1) {
						sess.setAttribute("outcome-read", Boolean.parseBoolean("false"));
						sess.setAttribute("outcome", "Password changed successfully!");
						return true;
					}
					else {
						sess.setAttribute("form-message-read", Boolean.parseBoolean("false"));
						sess.setAttribute("change-password-form-message", "Password changed failed.");
						return false;
					}
				}
				else {
					sess.setAttribute("form-message-read", Boolean.parseBoolean("false"));
					sess.setAttribute("change-password-form-message", "Password incorrect.");
					return false;
				}
			}
			else {
				sess.setAttribute("form-message-read", Boolean.parseBoolean("false"));
				sess.setAttribute("change-password-form-message", "Cannot find user.");
				return false;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("Unknown error when resetting password.");
		return false;
	}
	
	public boolean updateJSDetails(JobSeeker js, HttpSession sess, String newUsername) {
		try {
			_connectIfNec();
			if(!verifyJSUsernameExist(newUsername)) {
				String s = "update job_seekers set first_name=?, last_name=?, user_name=?, email=?, NRIC=?, contact=? where id=?";
				
				pstmt = con.prepareStatement(s);
				pstmt.setString(1, js.getFirstName().toUpperCase());
				pstmt.setString(2, js.getLastName().toUpperCase());
				pstmt.setString(3, newUsername.toUpperCase());
				pstmt.setString(4, js.getEmail().toUpperCase());
				pstmt.setString(5, js.getNRIC().toUpperCase());
				pstmt.setString(6, js.getContact());
				pstmt.setInt(7, js.getId());
				int out = pstmt.executeUpdate();
				if(out == 1) {
					sess.setAttribute("outcome-read", Boolean.parseBoolean("false"));
					sess.setAttribute("outcome", "Details updated.");
					return true;
				}
				else {
					sess.setAttribute("outcome-read", Boolean.parseBoolean("false"));
					sess.setAttribute("outcome", "Details update failed. Try again later.");
					return false;
				}
			}
			else {
				sess.setAttribute("outcome-read", Boolean.parseBoolean("false"));
				sess.setAttribute("outcome", "User name is already used.");
				return false;
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean updateJSDetails(JobSeeker js, HttpSession sess) {
		try {
			_connectIfNec();
			String s = "update job_seekers set first_name=?, last_name=?, email=?, NRIC=?, contact=? where id=?";
			
			pstmt = con.prepareStatement(s);
			pstmt.setString(1, js.getFirstName().toUpperCase());
			pstmt.setString(2, js.getLastName().toUpperCase());
			pstmt.setString(3, js.getEmail().toUpperCase());
			pstmt.setString(4, js.getNRIC().toUpperCase());
			pstmt.setString(5, js.getContact());
			pstmt.setInt(6, js.getId());
			int out = pstmt.executeUpdate();
			if(out == 1) {
				sess.setAttribute("outcome-read", Boolean.parseBoolean("false"));
				sess.setAttribute("outcome", "Details updated.");
				return true;
			}
			else {
				sess.setAttribute("outcome-read", Boolean.parseBoolean("false"));
				sess.setAttribute("outcome", "Details update failed. Try again later.");
				return false;
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean updateCredentials(JobSeeker js, List<Acad> acads, List<String> workExps, List<String> awards, HttpSession sess) {
		try {
			_connectIfNec();
			
			String s = "update job_seekers set ";
			
			if(!acads.isEmpty()) {
				s = s.concat("academic_1=?");
				for(int i=2;i<=acads.size();i++) {
					s = s.concat(", academic_"+i+"=?");
				}
			}
			if(!workExps.isEmpty()) {
				if(!acads.isEmpty()) {
					s = s.concat(", ");
				}
				s = s.concat("work_experience_1=?");
				for(int i=2;i<=workExps.size();i++) {
					s = s.concat(", work_experience_"+i+"=?");
				}
			}
			if(!awards.isEmpty()) {
				if(!acads.isEmpty() || !workExps.isEmpty()) {
					s = s.concat(", ");
				}
				s = s.concat("award_1=?");
				for(int i=2;i<=awards.size();i++) {
					s = s.concat(", award_"+i+"=?");
				}
			}
			if(!acads.isEmpty() || !workExps.isEmpty() || !awards.isEmpty()) {
				s = s.concat(" , years_of_exp=? ");
			}
			else {
				s = s.concat("years_of_exp=? ");
			}
			s = s.concat("where id=?");
			int totalFields = acads.size() + workExps.size() + awards.size() + 2;

			pstmt = con.prepareStatement(s);
			for(int i=1;i<=acads.size();i++) {
				pstmt.setString(i, acads.get(i-1).toString());
			}
			for(int i=acads.size()+1;i<=acads.size()+workExps.size();i++) {
				pstmt.setString(i, workExps.get(i-1-acads.size()).toString());
			}
			for(int i=acads.size()+workExps.size()+1;i<=acads.size()+workExps.size()+awards.size();i++) {
				pstmt.setString(i, awards.get(i-1-acads.size()-workExps.size()).toString());
			}
			pstmt.setInt(totalFields - 1, js.getYearsOfExperience());
			pstmt.setInt(totalFields, js.getId());
			int out = pstmt.executeUpdate();
			if(out == 1) {
				sess.setAttribute("outcome-read", Boolean.parseBoolean("false"));
				sess.setAttribute("outcome", "Credentials updated.");
				return true;
			}
			else {
				sess.setAttribute("outcome-read", Boolean.parseBoolean("false"));
				sess.setAttribute("outcome", "Credentials update failed. Try again later.");
				return false;
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean deleteJSAccount(JobSeeker js) {
		try {
			_connectIfNec();

			String a = "select id from applications where job_seeker_id=?";
			pstmt = con.prepareStatement(a);
			// Query all applications for the job posting.
			pstmt.setInt(1, js.getId());
			res = pstmt.executeQuery();
			List<Integer> appsToDelete = new ArrayList<Integer>();
			while (res.next()) {
				int appID = res.getInt(1);
				appsToDelete.add(appID);
			}

			// Delete all applications for the job posting.
			String d = "delete from applications where ";
			
			for (int i = 0; i < appsToDelete.size(); i++) {
				d = d.concat("id=? OR ");
			}
			d = d.concat("false");
			pstmt = con.prepareStatement(d);
			for (int i = 1; i <= appsToDelete.size(); i++) {
				pstmt.setInt(i, appsToDelete.get(i - 1));
			}
			int out = pstmt.executeUpdate();
			if (out == appsToDelete.size()) {
				String s = "delete from job_seekers where id=?";
				pstmt = con.prepareStatement(s);
				pstmt.setInt(1, js.getId());
				return pstmt.executeUpdate() == 1;
			} else {
				return false;
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean verifyJSEmailExists(String email, HttpSession sess) {
		try {
			_connectIfNec();
			
			String s = "select user_name from job_seekers where email=?";
			pstmt = con.prepareStatement(s);
			pstmt.setString(1, email.toUpperCase());
			res = pstmt.executeQuery();
			if(res.next()) {
				sess.setAttribute("reset-for-email", email);
				sess.setAttribute("loginAs", "jobSeeker");
				return true;
			}
			return false;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean applyJob(JobSeeker js, Job job, HttpSession sess) {
		JobApplication app = new JobApplication(
			js.getFirstName(),
			js.getLastName(),
			js.getContact(), js.getEmail(), js.getYearsOfExperience(), js.getNRIC(), job.getId());
		app.setCompanyName(job.getCompany_name());
		app.setCompanyEmail(job.getCompany_email());
		app.setJobRole(job.getRole());
		app.setJobDesc(job.getDesc());
		app.setSalary(job.getSalary());
		app.setYearsOfExp(job.getYears_of_exp());
		app.setApplicationCredential(new ArrayList<String>());
		if(js.getAcadDetails() != null) {
			for(Acad acad : js.getAcadDetails()) {
				app.addCredentials(acad.toString());
			}
		}
		if(js.getWorkExp()!=null) {
			for(String wkExp : js.getWorkExp()) {
				app.addCredentials(wkExp);
			}
		}
		if(js.getAwards()!=null) {
			for(String awd : js.getAwards()) {
				app.addCredentials(awd);
			}
		}
		app.setStatus(ApplicationStatus.Created);
		app.setJob_seeker_id(js.getId());
		return createJobApplication(app);
	}
	
	public Job loadJob(int jobID) {
		try {
			_connectIfNec();
			
			String s = "select * from jobs where id=?";
			pstmt = con.prepareStatement(s);
			pstmt.setInt(1, jobID);
			res = pstmt.executeQuery();
			if(res.next()) {
				Job job = new Job(
						res.getString(3),
						res.getString(4),
						res.getInt(5),
						res.getInt(6), this,
						res.getString(8),
						res.getString(9)
						);
				job.setId(res.getInt(1));
				job.setDatePosted(res.getDate(2));
				return job;
			}
			return null;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean acceptJobOffer(int appID) {
		try {
			_connectIfNec();
			
			String s = "update applications set application_status=? where id=?";
			pstmt = con.prepareStatement(s);
			pstmt.setString(1, ApplicationStatus.JobSeekerAccepted.toString());
			pstmt.setInt(2, appID);
			return pstmt.executeUpdate() == 1;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean cancelJobApplication(int appID) {
		try {
			_connectIfNec();
			
			String s = "update applications set application_status=? where id=?";
			pstmt = con.prepareStatement(s);
			pstmt.setString(1, ApplicationStatus.ApplicationRetracted.toString());
			pstmt.setInt(2, appID);
			return pstmt.executeUpdate() == 1;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
