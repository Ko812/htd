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
import com.ncs.model.SearchUtility;

/**
 * Servlet implementation class EditJobPosting
 */
public class EditJobPosting extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession sess = req.getSession();
		EmployerCompany ec = (EmployerCompany) sess.getAttribute("logged-in-employer");
		String jobRole = req.getParameter("jobRole");
		String jobDescription = req.getParameter("jobDescription");
		String salaryString = req.getParameter("salary");
		int salary, yearsOfExperience;
		SearchUtility su = new SearchUtility();
		if(salaryString != null && !salaryString.isEmpty() && su.isNumeric(salaryString, true)) {
			salary = Integer.parseInt(salaryString);
		}
		else {
			sess.setAttribute("edit-job-posting-form-message", "Salary is invalid");
			sess.setAttribute("form-message-read", Boolean.parseBoolean("false"));
			resp.sendRedirect("/job-portal/employer/employerDashboard.jsp?currentView=edit-job-posting");
			return;
		}
		String yoeString = req.getParameter("yearsOfExperience");
		if(yoeString != null && !yoeString.isEmpty() && su.isNumeric(yoeString, true)) {
			yearsOfExperience = Integer.parseInt(yoeString);
		}
		else {
			sess.setAttribute("edit-job-posting-form-message", "Years of experience is invalid");
			sess.setAttribute("form-message-read", Boolean.parseBoolean("false"));
			resp.sendRedirect("/job-portal/employer/employerDashboard.jsp?currentView=edit-job-posting");
			return;
		}
		Job job = (Job) sess.getAttribute("job-to-edit");
		job.setRole(jobRole);
		job.setDesc(jobDescription);
		job.setSalary(salary);
		job.setYears_of_exp(yearsOfExperience);
		if(ec.editJobPosting(job, sess) == 1) {
			sess.setAttribute("outcome", "Job posting updated successfully.");
			sess.setAttribute("outcome-read", Boolean.parseBoolean("false"));
			sess.setAttribute("success-failure", "success");
			sess.setAttribute("jobs", ec.viewJobPostings());
			resp.sendRedirect("/job-portal/employer/employerDashboard.jsp");
		}
		else {
			sess.setAttribute("outcome", "Job posting update unsuccessful.");
			sess.setAttribute("outcome-read", Boolean.parseBoolean("false"));
			sess.setAttribute("success-failure", "failure");
			resp.sendRedirect("/job-portal/employer/employerDashboard.jsp");
		}
	}

}
