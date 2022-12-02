package com.ncs.controller;

import java.io.IOException;
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
import com.ncs.model.JobApplication;
import com.ncs.model.SearchUtility;

/**
 * Servlet implementation class ViewJobApplications
 */
public class ViewJobApplications extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession sess = req.getSession();
		EmployerCompany ec = (EmployerCompany) sess.getAttribute("logged-in-employer");
		SearchUtility su = new SearchUtility(ec.db);
		String jobIDStr = req.getParameter("jobID");
		int jobID;
		if(jobIDStr == null || !su.isNumeric(jobIDStr, true)) {
			jobID = 9999;
		}
		else {
			jobID = Integer.parseInt(jobIDStr);
		}
		String viewBy = req.getParameter("viewBy");
		if(viewBy == null) {
			viewBy = "others";
		}
		String filterRole = req.getParameter("filterRole");
		String filterDatePosted = req.getParameter("filterDatePosted");
		String filterByMeetExpRqmt = req.getParameter("filterByMeetExpRqmt");
		
		if(filterRole == null || filterRole.equals("null")) {
			filterRole = "all";
		}
		if(filterDatePosted == null || filterDatePosted.equals("null")) {
			filterDatePosted = "all";
		}
		if(filterByMeetExpRqmt == null || filterByMeetExpRqmt.equals("null")) {
			filterByMeetExpRqmt = "false";
		}
		
		sess.setAttribute("filterRole", filterRole);
		sess.setAttribute("filterDatePosted", filterDatePosted);
		sess.setAttribute("filterByMeetExpRqmt", filterByMeetExpRqmt);
		
		List<JobApplication> apps;
		List<JobApplication> filteredApps;
		if(viewBy.equals("search")) {
			String searchJobApplications = req.getParameter("searchJobApplications");
			filteredApps = su.searchJobApplicationsInList(searchJobApplications, ec.getAllApplications());
			sess.setAttribute("filtered-apps", filteredApps);
			sess.setAttribute("search-field", searchJobApplications);
			if((String) sess.getAttribute("sortOption") != null) {
				resp.sendRedirect("/job-portal/EMSortApplications");
				return;
			}
			resp.sendRedirect("/job-portal/employer/employerDashboard.jsp?currentView=view-applications");
			return;
		}
		else {
			sess.setAttribute("search-field", "");
		}
		if(jobID == 9999) {
			apps = ec.getAllApplications();
			sess.setAttribute("all-applications", apps);
			sess.setAttribute("filtered-apps" , filter(filterRole, filterDatePosted, filterByMeetExpRqmt, apps));
			if((String) sess.getAttribute("sortOption") != null) {
				resp.sendRedirect("/job-portal/EMSortApplications");
				return;
			}
			resp.sendRedirect("/job-portal/employer/employerDashboard.jsp?currentView=view-applications");
		}
		else if(jobID >= 10000) {
			apps = ec.getJobApplications(jobID);
			sess.setAttribute("filtered-apps" , apps);
			if((String) sess.getAttribute("sortOption") != null) {
				resp.sendRedirect("/job-portal/EMSortApplications");
				return;
			}
			resp.sendRedirect("/job-portal/employer/employerDashboard.jsp?currentView=view-applications");
		}
		else {
			System.out.println("Invalid job id.");
			sess.setAttribute("outcome" , "Invalid job id");
			sess.setAttribute("outcome-read", Boolean.parseBoolean("false"));
			sess.setAttribute("success-failure", "failure");
			resp.sendRedirect("/job-portal/employer/employerDashboard.jsp");
		}
	}
	
	List<JobApplication> filter(String filterRole, String filterDatePosted, String filterByMeetExpRqmt, List<JobApplication> allApps){
		
		List<JobApplication> filteredJobRole = new ArrayList<JobApplication>();
		for(JobApplication app : allApps) {
			if(app.getJobRole().equals(filterRole) || filterRole.equals("all")) {
				filteredJobRole.add(app);
			}
		}
		List<JobApplication> filteredJobRoleAndDatePosted; 
		List<JobApplication> filteredApps;
		if(filterDatePosted.equals("all")) {
			if(filterByMeetExpRqmt.equals("false")) {
				filteredApps = filteredJobRole;
			}
			else {
				filteredApps = new ArrayList<JobApplication>();
				for(JobApplication app : filteredJobRole) {
					if(app.getYearsOfExp() <= app.getJsYearsOfExp()) {
						filteredApps.add(app);
					}
				}
			}
		}
		else {
			LocalDate today = LocalDate.now();
			int mth = Integer.parseInt(filterDatePosted.charAt(4) +"");
			LocalDate cutOff = today.minusDays(mth * 30);
			filteredJobRoleAndDatePosted = new ArrayList<JobApplication>();
			for(JobApplication app : filteredJobRole) {
				if(LocalDate.parse(app.getApplicationDate().toString()).isAfter(cutOff)) {
					filteredJobRoleAndDatePosted.add(app);
				}
			}
			if(filterByMeetExpRqmt.equals("false")) {
				filteredApps = filteredJobRoleAndDatePosted;
			}
			else {
				filteredApps = new ArrayList<JobApplication>();
				for(JobApplication app : filteredJobRoleAndDatePosted) {
					if(app.getYearsOfExp() <= app.getJsYearsOfExp()) {
						filteredApps.add(app);
					}
				}
			}
		}
		return filteredApps;
	}

}
