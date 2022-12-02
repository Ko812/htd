package com.ncs.model;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

public class JobSeeker {
	
	private int id;
	private String firstName;
	private String lastName;
	private String userName;
	
	private String NRIC;
	private List<Acad> acadDetails;
	private List<String> workExp;
	private List<String> awards;

	private int yearsOfExperience;
	private String contact;
	private String email;
	
	private int numberOfJobsApplied;
	
	public DBOps db;
	
	public JobSeeker(String firstName, String lastName, String userName, String nRIC, String contact, String email) {
		super();
		this.db = new DBOps();
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		NRIC = nRIC;
		this.contact = contact;
		this.email = email;
		this.acadDetails = new ArrayList<Acad>();
		this.workExp = new ArrayList<String>();
		this.awards = new ArrayList<String>();
	}
	
	public JobSeeker() {
		this.db = new DBOps();
		this.acadDetails = new ArrayList<Acad>();
		this.workExp = new ArrayList<String>();
		this.awards = new ArrayList<String>();
	}
	
	public JobSeeker(DBOps db) {
		this.acadDetails = new ArrayList<Acad>();
		this.workExp = new ArrayList<String>();
		this.awards = new ArrayList<String>();
	}
	
	public JobSeeker(String firstName, String lastName, String userName, String nRIC, String contact, String email, DBOps db) {
		super();
		this.db = db;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		NRIC = nRIC;
		this.contact = contact;
		this.email = email;
	}

	public static JobSeeker login(String userName, String password, HttpSession sess) {
		JobSeeker js = new JobSeeker();
		return js.db.loginJobSeeker(userName, password, sess);
	}
	
	public boolean register(String password, HttpSession sess) {
		return db.createJobSeeker(this, password, sess);
	}
	
	public boolean changePassword(String oldPassword, String newPassword, HttpSession sess) {
		return db.changePassword(this, oldPassword, newPassword, sess);
	}
	
	public boolean updateDetails(HttpSession sess, String newUsername) {
		return db.updateJSDetails(this, sess, newUsername);
	}
	
	public boolean updateDetails(HttpSession sess) {
		return db.updateJSDetails(this, sess);
	}
	
	public boolean updateCredentials(List<Acad> acads, List<String> workExps, List<String> awards, HttpSession sess) {
		return db.updateCredentials(this, acads, workExps, awards, sess);
	}
	
	public boolean delete() {
		if(db.deleteJSAccount(this)) {
			db.deleteAllJobApplications(this.id);
			return true;
		}
		return false;
	}
	
	public void addAcad(String acad) {
		if(acadDetails == null) {
			this.acadDetails = new ArrayList<Acad>();
		}
		acad = acad.replace(" ", "");
		acadDetails.add(Acad.valueOf(acad));
	}
	
	public void addWorkExp(String exp) {
		if(workExp == null) {
			this.workExp = new ArrayList<String>();
		}
		workExp.add(exp);
	}
	
	public void addAward(String awd) {
		if(awards == null) {
			this.awards = new ArrayList<String>();
		}
		awards.add(awd);
	}
	
	public void removeWork(int ind) {
		if(workExp != null && !workExp.isEmpty() && workExp.size() - 1 >= ind) {
			workExp.remove(ind);
		}
	}
	
	public void removeAcad(int ind) {
		if(acadDetails != null && !acadDetails.isEmpty() && acadDetails.size() - 1 >= ind) {
			acadDetails.remove(ind);
		}
	}
	
	public void removeAward(int ind) {
		if(awards != null && !awards.isEmpty() && awards.size() - 1 >= ind) {
			awards.remove(ind);
		}
	}
	
	public boolean applyJob(Job job, HttpSession sess) {
		return db.applyJob(this, job, sess);
	}
	
	public Job loadJob(int jobID) {
		return db.loadJob(jobID);
	}
	
	public List<JobApplication> pullJobsApplied(){
		List<JobApplication> apps = db.pullJSJobsApplied(this.id);
		this.setNumberOfJobsApplied(apps.size());
		return apps;
	}
	
	public boolean editJobApplication(JobApplication app, HttpSession sess) {
		return db.editJobApplication(app, sess);
	}
	
	public boolean updateApplicationStatus(int appID, String updateStatus) {
		return db.updateAppStatus(appID, updateStatus);
	}
	
	public boolean acceptJobOffer(int appID) {
		return db.acceptJobOffer(appID);
	}
	
	public boolean cancelJobApplication(int appID) {
		return db.cancelJobApplication(appID);
	}
	
	/**************************************
	 * Getters and Setters
	 * @return
	 **************************************/
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getNRIC() {
		return NRIC;
	}
	
	public void setNRIC(String nRIC) {
		NRIC = nRIC;
	}
	
	public List<Acad> getAcadDetails() {
		return acadDetails;
	}
	
	public void setAcadDetails(List<Acad> acadDetails) {
		this.acadDetails = acadDetails;
	}
	
	public int getYearsOfExperience() {
		return yearsOfExperience;
	}
	
	public void setYearsOfExperience(int yearsOfExperience) {
		this.yearsOfExperience = yearsOfExperience;
	}
	
	public String getContact() {
		return contact;
	}
	
	public void setContact(String contact) {
		this.contact = contact;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public List<String> getWorkExp() {
		return workExp;
	}

	public void setWorkExp(List<String> workExp) {
		this.workExp = workExp;
	}

	public List<String> getAwards() {
		return awards;
	}

	public void setAwards(List<String> awards) {
		this.awards = awards;
	}
	
	
	
	public int getNumberOfJobsApplied() {
		return numberOfJobsApplied;
	}

	public void setNumberOfJobsApplied(int numberOfJobsApplied) {
		this.numberOfJobsApplied = numberOfJobsApplied;
	}

	@Override
	public String toString() {
		return "JobSeeker [id=" + id + ", firstName=" + firstName + "]";
	}
}
