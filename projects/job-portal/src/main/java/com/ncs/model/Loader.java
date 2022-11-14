package com.ncs.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/*************************************
Loader to load the employer, jobs and job
seekers data into the database.

Place the csv files in a convenient location,
and run the relevant methods to load.

Depending on your database definition, if 
you matched foreign keys between, say for example,
employers and jobs, then load employers data first,
followed by jobs, then finally job seekers data.

*Pending generating job applications data.*

**************************************/
public class Loader {
	
	public static PreparedStatement pstmt;
	public static Scanner reader = new Scanner(System.in);
	public final static String path = "c:\\users\\user\\desktop\\ncs\\job-portal\\job_portal_job_data.csv";
	public final static String un = "java_developer";
	public final static String pwd = "Password123!";
	public final static Random rand = new Random();
	
	public static void main(String[] args) throws IOException {
		updateEmployerSize(un, pwd);
	}
	
	public static void updateEmployersData(String fullPath, String dbUsername, String password) {
		try {
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/job_portal", dbUsername, password);
			
			//e.g. fullPath = "c:\\job-portal\\job_portal_employer_data.csv";

			FileReader fr = new FileReader(fullPath);
			BufferedReader br = new BufferedReader(fr);
			
			String x = br.readLine();

			while((x = br.readLine()) != null) {
				System.out.println(x);
				String[] line = x.split(",");
				String s = "update employers set password=? where user_name=?";
				pstmt = con.prepareStatement(s);
				pstmt.setString(1, Encryption.encrypt(line[3]));
				pstmt.setString(2, line[2].toUpperCase());
				int out = pstmt.executeUpdate();
				System.out.println("View query: " + pstmt);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void loadEmployersData(String fullPath, String dbUsername, String password) {
		try {
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/job_portal", dbUsername, password);
			
			//e.g. fullPath = "c:\\job-portal\\job_portal_employer_data.csv";

			FileReader fr = new FileReader(fullPath);
			BufferedReader br = new BufferedReader(fr);
			
			String x = br.readLine();

			while((x = br.readLine()) != null) {
				System.out.println(x);
				String[] line = x.split(",");
				String s = "insert into employers (company_name, user_name, password, company_email) values (?, ?, ?, ?)";
				pstmt = con.prepareStatement(s);
				pstmt.setString(1, line[1]);
				pstmt.setString(2, line[2].toUpperCase());
				System.out.println(line[2]+" : "+line[3]);
				pstmt.setString(3, Encryption.encrypt(line[3]));
				pstmt.setString(4, line[4].toUpperCase());
				int out = pstmt.executeUpdate();
				System.out.println("View query: " + pstmt);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void loadJobSeekersData(String fullPath, String dbUsername, String password) {
		try {
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/job_portal", dbUsername, password);
			
			// e.g. fullPath = "c:\\job-portal\\job_portal_job_seeker_data.csv";

			FileReader fr = new FileReader(fullPath);
			BufferedReader br = new BufferedReader(fr);
			
			String x = br.readLine();
			int i = 0;
			while((x = br.readLine()) != null) {
				System.out.println(x);
				String[] line = x.split(",");
				String s = "insert into job_seekers (first_name, last_name, user_name, password, email, NRIC, contact, years_of_exp, academic_1) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
				pstmt = con.prepareStatement(s);
				pstmt.setString(1, line[1]);
				pstmt.setString(2, line[2]);
				pstmt.setString(3, line[3].toUpperCase());
				pstmt.setString(4, Encryption.encrypt(line[4]));
				pstmt.setString(5, line[5]);
				pstmt.setString(6, line[6]);
				pstmt.setString(7, line[7]);
				pstmt.setInt(8, Integer.parseInt(line[8]));
				pstmt.setString(9, line[9]);
				int out = pstmt.executeUpdate();
				System.out.println(++i + "th execution " + pstmt);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void loadJobsData(String fullPath, String dbUsername, String password) {
		try {
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/job_portal", dbUsername, password);
			
			// e.g. fullPath = "c:\\job-portal\\job_portal_job_data.csv";

			FileReader fr = new FileReader(fullPath);
			BufferedReader br = new BufferedReader(fr);
			
			String x = br.readLine();

			while((x = br.readLine()) != null) {
				System.out.println(x);
				String[] line = x.split(",");
				String s = "insert into jobs (date_posted, job_role, job_desc, salary, years_of_exp, employer_id, company_name, company_email) values (?, ?, ?, ?, ?, ?, ?, ?)";
				pstmt = con.prepareStatement(s);
				String dateStr = line[1].replace("/","-");
				System.out.println(dateStr);
				pstmt.setDate(1, Date.valueOf(dateStr));
				pstmt.setString(2, line[2].toUpperCase());
				pstmt.setString(3, line[3]);
				pstmt.setInt(4, Integer.parseInt(line[4]));
				pstmt.setInt(5, Integer.parseInt(line[5]));
				pstmt.setInt(6, Integer.parseInt(line[6]));
				pstmt.setString(7, line[7]);
				pstmt.setString(8, line[8]);
				int out = pstmt.executeUpdate();
				System.out.println("Executed " + pstmt);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void generateApplications(String dbUsername, String password) {
		try {
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/job_portal", dbUsername, password);
			DBOps db = new DBOps(con);
			
			String j = "select * from jobs";
			pstmt = con.prepareStatement(j);
			ResultSet allJobs = pstmt.executeQuery();
			
			String js = "select * from job_seekers";
			pstmt = con.prepareStatement(js);
			ResultSet allJobSeekers = pstmt.executeQuery();
			
			List<JobSeeker> jsList = parseJobSeekers(allJobSeekers, db);
			List<Job> jobList = parseJobs(allJobs, db);
			int applicationsGenerated = 0;
			int totalJobSeekers = jsList.size();
			for(Job job : jobList) {
				int totalApplicants = rand.nextInt(4, 8);
				List<Integer> applicantIndices = new ArrayList<Integer>();
				for(int i=0;i<totalApplicants;i++) {
					int genNum;
					do {
						genNum = rand.nextInt(0, totalJobSeekers);
					}while(applicantIndices.contains(genNum));
					applicantIndices.add(genNum);
				}
				for(int jsID : applicantIndices) {
					JobSeeker jobSeeker = jsList.get(jsID);
					if(db.applyJob(jobSeeker, job, null)) {
						applicationsGenerated++;
						System.out.println("Generated " + applicationsGenerated + " applications");
					}
					else {
						System.out.println("Cannot generated application between js" + jobSeeker.getId() + " and job" +job.getId());
					}
					
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static List<JobSeeker> parseJobSeekers(ResultSet res, DBOps db){
		List<JobSeeker> jsList = new ArrayList<JobSeeker>();
		try {
			while(res.next()) {
				JobSeeker js = new JobSeeker(db);
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
//				js.setPersonalWebsite(res.getString(15));
				for(int i = 0; i < 5; i++) {
					String award = res.getString(21 + i);
					if(award != null) {
						js.addAward(award);
					}
				}
				jsList.add(js);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return jsList;
	}
	
	public static List<Job> parseJobs(ResultSet res, DBOps db){
		List<Job> jobList = new ArrayList<Job>();
		try {
			while(res.next()) {
				Job job = new Job(
						res.getString(3),
						res.getString(4),
						res.getInt(5),
						res.getInt(6), db,
						res.getString(8),
						res.getString(9)
						);
				job.setId(res.getInt(1));
				job.setDatePosted(res.getDate(2));
				job.setEmployer_id(res.getInt(7));
				jobList.add(job);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return jobList;
	}
	
	public static void updateEmployerSize(String dbUsername, String password) {
		try {
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/job_portal", dbUsername, password);
			
			String s = "update employers set company_size=1, company_details=\"\" where true";
			pstmt = con.prepareStatement(s);
			int out =pstmt.executeUpdate();
			System.out.println(out);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
