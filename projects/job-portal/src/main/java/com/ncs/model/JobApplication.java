package com.ncs.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class JobApplication {
	
	private int id;
	private String companyName;
	private String companyEmail;
	private String jobRole;
	private String jobDesc;
	private int salary;
	private int yearsOfExp;
	private Date applicationDate;
	private ApplicationStatus status;
	private String jsFirstName;
	private String jsLastName;
	private String jsContact;
	private String jsEmail;
	private int jsYearsOfExp;
	private String jsIdentificationNumber;
	private List<String> applicationCredential;
	private int job_id;
	private int job_seeker_id;

	public int getJob_seeker_id() {
		return job_seeker_id;
	}

	public void setJob_seeker_id(int job_seeker_id) {
		this.job_seeker_id = job_seeker_id;
	}

	public JobApplication(String jsFirstName, String jsLastName, String jsContact, String jsEmail, int jsYearsOfExp,
			String jsIdentificationNumber, int job_id) {
		super();
		this.jsFirstName = jsFirstName;
		this.jsLastName = jsLastName;
		this.jsContact = jsContact;
		this.jsEmail = jsEmail;
		this.jsYearsOfExp = jsYearsOfExp;
		this.jsIdentificationNumber = jsIdentificationNumber;
		this.job_id = job_id;
	}
	
	public JobApplication() {
		
	}
	
	public void addCredentials(String cred) {
		if(applicationCredential == null) {
			applicationCredential = new ArrayList<String>();
		}
		applicationCredential.add(cred);
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

	public String getCompanyEmail() {
		return companyEmail;
	}

	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
	}

	public List<String> getApplicationCredential() {
		return applicationCredential;
	}
	public void setApplicationCredential(List<String> applicationCredential) {
		this.applicationCredential = applicationCredential;
	}
	public int getJob_id() {
		return job_id;
	}
	public void setJob_id(int job_id) {
		this.job_id = job_id;
	}
	
	public Date getApplicationDate() {
		return applicationDate;
	}

	public void setApplicationDate(Date date) {
		this.applicationDate = date;
	}
	
	public ApplicationStatus getStatus() {
		return status;
	}
	
	public void setStatus(ApplicationStatus status) {
		this.status = status;
	}
	
	public String getJsFirstName() {
		return jsFirstName;
	}

	public void setJsFirstName(String jsFirstName) {
		this.jsFirstName = jsFirstName;
	}
	public String getJsLastName() {
		return jsLastName;
	}
	public void setJsLastName(String jsLastName) {
		this.jsLastName = jsLastName;
	}
	public String getJsContact() {
		return jsContact;
	}
	public void setJsContact(String jsContact) {
		this.jsContact = jsContact;
	}
	public String getJsEmail() {
		return jsEmail;
	}
	public void setJsEmail(String jsEmail) {
		this.jsEmail = jsEmail;
	}
	public int getJsYearsOfExp() {
		return jsYearsOfExp;
	}
	public void setJsYearsOfExp(int jsYearsOfExp) {
		this.jsYearsOfExp = jsYearsOfExp;
	}
	public String getJsIdentificationNumber() {
		return jsIdentificationNumber;
	}
	public void setJsIdentificationNumber(String jsIdentificationNumber) {
		this.jsIdentificationNumber = jsIdentificationNumber;
	}

	public String getJobRole() {
		return jobRole;
	}

	public void setJobRole(String jobRole) {
		this.jobRole = jobRole;
	}

	public String getJobDesc() {
		return jobDesc;
	}
	public void setJobDesc(String jobDesc) {
		this.jobDesc = jobDesc;
	}
	public int getSalary() {
		return salary;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}
	public int getYearsOfExp() {
		return yearsOfExp;
	}
	public void setYearsOfExp(int yearsOfExp) {
		this.yearsOfExp = yearsOfExp;
	}
	
}
