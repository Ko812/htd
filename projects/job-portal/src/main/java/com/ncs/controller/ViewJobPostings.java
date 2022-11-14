package com.ncs.controller;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
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
 * Servlet implementation class ViewJobPostings
 */
public class ViewJobPostings extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession sess = req.getSession();
		EmployerCompany ec = (EmployerCompany) sess.getAttribute("logged-in-employer");
		List<Job> jobs = ec.viewJobPostings();
		sess.setAttribute("jobs", jobs);
		String viewBy = req.getParameter("viewBy");
		if(viewBy == null) {
			viewBy = "others";
		}
		String filterRole = req.getParameter("filterRole");
		String filterDatePosted = req.getParameter("filterDatePosted");
		String filterHasApplicants = req.getParameter("filterHasApplicant");
		
		if(filterRole == null || filterRole.equals("null")) {
			filterRole = "all";
		}
		if(filterDatePosted == null || filterDatePosted.equals("null")) {
			filterDatePosted = "all";
		}
		if(filterHasApplicants == null || filterHasApplicants.equals("null")) {
			filterHasApplicants = "false";
		}
		
		sess.setAttribute("filterRole", filterRole);
		sess.setAttribute("filterDatePosted", filterDatePosted);
		sess.setAttribute("filterHasApplicant", filterHasApplicants);
		
		List<Job> filteredJobRole = new ArrayList<Job>();
		List<Job> filteredJobRoleAndDatePosted; 
		List<Job> filteredJobs;
		
		if(viewBy.equals("search")) {
			String searchJobPosting = req.getParameter("searchJobPosting");
			SearchUtility su = new SearchUtility(ec.db);
			filteredJobs = su.searchPostingsInList(searchJobPosting, jobs);
			sess.setAttribute("filtered-postings", filteredJobs);
			sess.setAttribute("search-field", searchJobPosting);
			resp.sendRedirect("/job-portal/employer/employerDashboard.jsp?currentView=view-job-postings");
			return;
		}
		else {
			sess.setAttribute("search-field", "");
		}

		
		for(Job job : jobs) {
			if(job.getRole().equals(filterRole) || filterRole.equals("all")) {
				filteredJobRole.add(job);
			}
		}
		 
		if(filterDatePosted.equals("all")) {
			if(filterHasApplicants.equals("false")) {
				filteredJobs = filteredJobRole;
			}
			else {
				filteredJobs = new ArrayList<Job>();
				for(Job job : filteredJobRole) {
					if(job.getApplicants().size() > 0) {
						filteredJobs.add(job);
					}
				}
			}
		}
		else {
			LocalDate today = LocalDate.now();
			int mth = Integer.parseInt(filterDatePosted.charAt(4) +"");
			LocalDate cutOff = today.minusDays(mth * 30);
			filteredJobRoleAndDatePosted = new ArrayList<Job>();
			for(Job job : filteredJobRole) {
				if(LocalDate.parse(job.getDatePosted().toString()).isAfter(cutOff)) {
					filteredJobRoleAndDatePosted.add(job);
				}
			}
			if(filterHasApplicants.equals("false")) {
				filteredJobs = filteredJobRoleAndDatePosted;
			}
			else {
				filteredJobs = new ArrayList<Job>();
				for(Job job : filteredJobRoleAndDatePosted) {
					if(job.getApplicants().size() > 0) {
						filteredJobs.add(job);
					}
				}
			}
		}
		sess.setAttribute("filtered-postings", filteredJobs);
		resp.sendRedirect("/job-portal/employer/employerDashboard.jsp?currentView=view-job-postings");
	}
}
