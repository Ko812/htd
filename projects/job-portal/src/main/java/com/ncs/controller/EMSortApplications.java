package com.ncs.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ncs.model.EmployerCompany;
import com.ncs.model.JobApplication;
import com.ncs.model.SearchUtility;

/**
 * Servlet implementation class EMSortApplications
 */
public class EMSortApplications extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession sess = req.getSession();
		EmployerCompany ec = (EmployerCompany) sess.getAttribute("logged-in-employer");
		
		@SuppressWarnings("unchecked")
		List<JobApplication> filteredApps = (List<JobApplication>) sess.getAttribute("filtered-apps");
		if(filteredApps == null) {
			filteredApps = (List<JobApplication>) sess.getAttribute("all-applications");
			if(filteredApps == null) {
				resp.sendRedirect("/job-portal/ViewJobApplications");
			}
		}
		
		String sortOption = req.getParameter("sortOption");
		if(sortOption == null) {
			sortOption = (String) sess.getAttribute("sortOption");
		}
		else {
			sess.setAttribute("sortOption", sortOption);
		}
		if(sortOption == null) {
			resp.sendRedirect("/job-portal/employer/employerDashboard.jsp?currentView=view-applications");
			return;
		}
		if(sortOption.equals("AppExp")) {
			Comparator<JobApplication> c = (a, b) -> b.getJsYearsOfExp() - a.getJsYearsOfExp();
			filteredApps.sort(c);
		}
		else if (sortOption.equals("AppDate")) {
			Comparator<JobApplication> c = (a, b) -> b.getApplicationDate().compareTo(a.getApplicationDate());
			filteredApps.sort(c);
		}
		else if (sortOption.equals("AppStatus")) {
			Comparator<JobApplication> c = (a, b) -> a.getStatus().compareTo(b.getStatus());
			filteredApps.sort(c);
		}
		else if (sortOption.equals("AppCred")) {
			Comparator<JobApplication> c = (a, b) -> a.getApplicationCredential().get(0).compareTo(b.getApplicationCredential().get(0));
			filteredApps.sort(c);
		}
		sess.setAttribute("filtered-apps", filteredApps);
		resp.sendRedirect("/job-portal/employer/employerDashboard.jsp?currentView=view-applications");
	}

}
