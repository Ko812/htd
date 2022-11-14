package com.ncs.model;

import java.util.ArrayList;
import java.util.List;

public class SearchUtility {
	DBOps db;
	String searchText;
	String searchBy;
	List<Job> searchResults;
	
	public SearchUtility(String searchText, String searchBy) {
		super();
		this.db = new DBOps();
		this.searchText = searchText.toUpperCase();
		this.searchBy = searchBy;
	}
	public SearchUtility() {
		this.db = new DBOps();
	}
	
	public SearchUtility(DBOps db) {
		this.db = db;
	}
	
	public List<Job> search(){
		if(searchBy.equals("byJobRole")) {
			searchResults = db.searchByJobRole(patSearchText(searchText));
		}
		else if (searchBy.equals("byJobYearsOfExp")) {
			if(isNumeric(searchText, false)) {
				searchResults = db.searchByYOE(Integer.parseInt(searchText));
			}
			else {
				searchResults = new ArrayList<Job>();
			}
		}
		else if(searchBy.equals("byCompanyName")) {
			searchResults = db.searchByCompanyName(patSearchText(searchText));
		}
		else {
			if(isNumeric(searchText, true)) {
				searchResults = db.searchBySalary(Integer.parseInt(searchText));
			}
			else {
				searchResults = new ArrayList<Job>();
			}
			
		}
		return searchResults;
	}
	
	public List<String> patSearchText(String text) {
		String[] words = text.split(" ");
		List<String> patWords = new ArrayList<String>();
		patWords.add(text+"%");
		for(int i=0;i<words.length;i++) {
			if((!words[i].equals("PTE")) && (!words[i].equals("LTD"))) {
				patWords.add("%" + words[i] + "%");
			}
		}
		return patWords;
	}
	
	
	public String getSearchText() {
		return searchText;
	}
	public void setSearchText(String searchText) {
		this.searchText = searchText.toUpperCase();
	}
	public String getSearchBy() {
		return searchBy;
	}
	public void setSearchBy(String searchBy) {
		this.searchBy = searchBy;
	}
	
	public boolean isNumeric(String str, boolean digitsOnly) {
		String digits = "0123456789";
		if(digitsOnly) {
			for(int i = 0; i<str.length();i++) {
				if(!digits.contains(str.charAt(i) + "")) {
					return false;
				}
			}
		}
		else {
			int modifierOccurence = 0;
			for(int i = 0; i<str.length();i++) {
				if((str.charAt(i) + "").equals(".")) {
					if(modifierOccurence >0) {
						return false;
					}
					else {
						modifierOccurence++;
					}
				}
				if(!digits.contains(str.charAt(i) + "")) {
					return false;
				}
			}
		}
		return true;
	}
	
	public List<Job> searchPostingsInList(String searchText, List<Job> jobs){
		List<Job> shortlistedJobs = new ArrayList<Job>();
		searchText = searchText.toUpperCase();
		for(Job job : jobs) {
			if(job.getRole().contains(searchText)) {
				shortlistedJobs.add(job);
			}
		}
		return shortlistedJobs;
	}
	
	public List<JobApplication> searchJobApplicationsInList(String searchText, List<JobApplication> apps){
		List<JobApplication> shortlistedApplications = new ArrayList<JobApplication>();
		searchText = searchText.toUpperCase();
		for(JobApplication job : apps) {
			if(job.getJobRole().contains(searchText)) {
				shortlistedApplications.add(job);
			}
		}
		return shortlistedApplications;
	}
}
