package com.ncs.model;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

public class EmployerCompany {
	private int id;
	private String companyName;
	private String userName;
	private String companyEmail;
	private String companyDetails;
	private int yearsOfOperation;
	private String vmv;
	private String companyWebsite;
	private CompanySize companySize;
	public DBOps db;

	public EmployerCompany(String companyName, String userName, String companyEmail) {
		super();
		this.companyName = companyName;
		this.userName = userName;
		this.companyEmail = companyEmail;
		this.db = new DBOps();
	}
	
	public EmployerCompany() {
		this.db = new DBOps();
	}
	
	public EmployerCompany(String companyName, String userName, String companyEmail, DBOps db) {
		super();
		this.companyName = companyName;
		this.userName = userName;
		this.companyEmail = companyEmail;
		this.db = db;
	}
	
	public boolean register(String password, HttpSession sess) {
		return db.createEmployer(this, password, sess);
	}
	
	public static EmployerCompany login(String userName, String password, HttpSession sess) {
		EmployerCompany ec = new EmployerCompany();
		return ec.db.loginEmployer(userName, password, sess);
	}
	
	public Integer updateEmployer() {
		return db.updateEmployer(this);
	}
	
	public boolean delete() {
		return db.deleteEMAccount(this);
	}
	
	public Integer createJobPosting(Job job) {
		return db.createJob(job);
	}
	
	public Integer editJobPosting(Job job) {
		return db.editJob(job);
	}
	
	public Integer deleteJobPosting(Job job) {
		return db.deleteJob(job);
	}
	
	public Integer deleteJobPosting(int jobID) {
		return db.deleteJob(jobID);
	}
	
	public List<Job> viewJobPostings(){
		// Default to get all job postings
		List<Job> jobs = db.getAllJobPostings(id);
		return db.getAllJobApplicantsForAllJobs(jobs);
	}
	
	public List<JobApplication> getAllApplications(){
		return db.getAllApplications(this.id);
	}
	
	public List<JobApplication> getJobApplications(int jobID){
		return db.getJobApplications(jobID);
	}
	
	public boolean updateApplicationStatus(int appID, String appStatus) {
		return db.updateAppStatus(appID, appStatus);
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
	
	public String getCompanyName() {
		return companyName;
	}
	
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getCompanyEmail() {
		return companyEmail;
	}
	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
	}
	
	public String getCompanyDetails() {
		return companyDetails;
	}
	
	public void setCompanyDetails(String companyDetails) {
		this.companyDetails = companyDetails;
	}
	
	public int getYearsOfOperation() {
		return yearsOfOperation;
	}
	
	public void setYearsOfOperation(int yearsOfOperation) {
		this.yearsOfOperation = yearsOfOperation;
	}
	
	public String getVmv() {
		return vmv;
	}
	
	public void setVmv(String vmv) {
		this.vmv = vmv;
	}
	
	public String getCompanyWebsite() {
		return companyWebsite;
	}
	
	public void setCompanyWebsite(String companyWebsite) {
		this.companyWebsite = companyWebsite;
	}
	
	public CompanySize getCompanySize() {
		return companySize;
	}
	
	public int getCompanySize(boolean inInteger) {
		if(inInteger) {
			switch(companySize) {
			case Small:
				return 1;
			case SmallMedium:
				return 2;
			case Medium:
				return 3;
			case MediumLarge:
				return 4;
			case Large:
				return 5;
			case Transnational:
				return 6;
			case Multinational:
				return 7;
			default:
				return 0;	
			}
		}else {
			return 0;
		}
	}
	
	public void s(CompanySize companySize) {
		this.companySize = companySize;
	}
	public void setCompanySize(CompanySize companySize) {
		this.companySize = companySize;
	}
	
	public void setCompanySize(int companySize) {
		switch(companySize) {
			case 1:
				this.companySize = CompanySize.Small;
				return;
			case 2:
				this.companySize = CompanySize.SmallMedium;
				return;
			case 3:
				this.companySize = CompanySize.Medium;
				return;
			case 4:
				this.companySize = CompanySize.MediumLarge;
				return;
			case 5:
				this.companySize = CompanySize.Large;
				return;
			case 6:
				this.companySize = CompanySize.Transnational;
				return;
			case 7:
				this.companySize = CompanySize.Multinational;
				return;
			default:
				this.companySize = CompanySize.Small;
				return;	
		}
	}
	
	@Override
	public String toString() {
		return "EmployerCompany [id=" + id + ", companyName=" + companyName + ", userName=" + userName
				+ ", companyEmail=" + companyEmail + ", companyDetails=" + companyDetails + ", yearsOfOperation="
				+ yearsOfOperation + ", vmv=" + vmv + ", companyWebsite=" + companyWebsite + ", companySize="
				+ companySize + "]";
	}
	
}
