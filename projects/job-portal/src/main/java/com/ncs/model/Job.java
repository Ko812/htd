package com.ncs.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Job {
	
	private int id;
	private String role;
	private int salary;
	private int years_of_exp;
	private String desc;
	private Date datePosted;
	
	private int employer_id;
	private String company_name;
	private String company_email;
	private List<JobSeeker> applicants;
	private JobStatus status;
	private DBOps db;

	public Job(String role, String desc, int salary, int years_of_exp, DBOps db, String companyName, String companyEmail) {
		super();
		this.role = role;
		this.salary = salary;
		this.years_of_exp = years_of_exp;
		this.desc = desc;
		this.company_name = companyName;
		this.company_email = companyEmail;
		this.db = db;
	}
	
	public Job() {
		
	}

	public void addApplicant(JobSeeker js) {
		if(applicants == null) {
			applicants = new ArrayList<JobSeeker>();
		}
		applicants.add(js);
	}
	
	public void retrieveApplicantsData() {
		if(applicants == null) {
			applicants = new ArrayList<JobSeeker>();
		}
		applicants = db.getAllJobApplicants(this);
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
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public int getSalary() {
		return salary;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}
	public int getYears_of_exp() {
		return years_of_exp;
	}
	public void setYears_of_exp(int years_of_exp) {
		this.years_of_exp = years_of_exp;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public int getEmployer_id() {
		return employer_id;
	}
	public void setEmployer_id(int employer_id) {
		this.employer_id = employer_id;
	}
	public List<JobSeeker> getApplicants() {
		return applicants;
	}
	public void setApplicants(List<JobSeeker> applicants) {
		this.applicants = applicants;
	}
	public Date getDatePosted() {
		return datePosted;
	}

	public void setDatePosted(Date datePosted) {
		this.datePosted = datePosted;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getCompany_email() {
		return company_email;
	}

	public void setCompany_email(String company_email) {
		this.company_email = company_email;
	}

	public JobStatus getStatus() {
		return status;
	}

	public void setStatus(JobStatus status) {
		this.status = status;
	}
	
}
