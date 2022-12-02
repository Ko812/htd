package com.ncs.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ncs.model.EmployerCompany;
import com.ncs.model.Job;
import com.ncs.model.JobStatus;
import com.ncs.model.SearchUtility;


public class CreateJobPosting extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession sess = req.getSession();
		EmployerCompany ec = (EmployerCompany) sess.getAttribute("logged-in-employer");
		String jobRole = req.getParameter("jobRole");
		if(jobRole == null || jobRole.isEmpty()) {
			sess.setAttribute("create-job-posting-form-message", "Job role is required");
			sess.setAttribute("form-message-read", Boolean.parseBoolean("false"));
			resp.sendRedirect("/job-portal/employer/employerDashboard.jsp?currentView=create-job-posting");
			return;
		}
		String jobDescription = req.getParameter("jobDescription");
		if(jobDescription == null || jobDescription.isEmpty()) {
			sess.setAttribute("create-job-posting-form-message", "Job description is required");
			sess.setAttribute("form-message-read", Boolean.parseBoolean("false"));
			resp.sendRedirect("/job-portal/employer/employerDashboard.jsp?currentView=create-job-posting");
			return;
		}
		String salaryString = req.getParameter("salary");
		int salary, yearsOfExperience;
		SearchUtility su = new SearchUtility();
		if(salaryString != null && !salaryString.isEmpty() && su.isNumeric(salaryString, true)) {
			salary = Integer.parseInt(salaryString);
		}
		else {
			sess.setAttribute("create-job-posting-form-message", "Salary is invalid");
			sess.setAttribute("form-message-read", Boolean.parseBoolean("false"));
			resp.sendRedirect("/job-portal/employer/employerDashboard.jsp?currentView=create-job-posting");
			return;
		}
		String yoeString = req.getParameter("yearsOfExperience");
		if(yoeString != null && !yoeString.isEmpty() && su.isNumeric(yoeString, true)) {
			yearsOfExperience = Integer.parseInt(yoeString);
		}
		else {
			sess.setAttribute("create-job-posting-form-message", "Years of experience is invalid");
			sess.setAttribute("form-message-read", Boolean.parseBoolean("false"));
			resp.sendRedirect("/job-portal/employer/employerDashboard.jsp?currentView=create-job-posting");
			return;
		}
		Job job = new Job(jobRole, jobDescription, salary, yearsOfExperience, ec.db, ec.getCompanyName(), ec.getCompanyEmail());
		job.setEmployer_id(ec.getId());
		job.setStatus(JobStatus.Open);
		int outcome = ec.createJobPosting(job);
		if(outcome == 1) {
			sess.setAttribute("outcome", "Job posting created successfully!");
			sess.setAttribute("outcome-read", Boolean.parseBoolean("false"));
			sess.setAttribute("success-failure", "success");
			resp.sendRedirect("/job-portal/ViewJobPostings");
		}
		else {
			sess.setAttribute("outcome", "Job posting creation unsuccessful.");
			sess.setAttribute("outcome-read", Boolean.parseBoolean("false"));
			sess.setAttribute("success-failure", "failure");
			resp.sendRedirect("/job-portal/employer/employerDashboard.jsp");
		}
	}

}
