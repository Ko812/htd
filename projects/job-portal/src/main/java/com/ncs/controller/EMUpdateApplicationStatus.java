package com.ncs.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ncs.model.ApplicationStatus;
import com.ncs.model.EmployerCompany;
import com.ncs.model.JobApplication;

/**
 * Servlet implementation class EMUpdateApplicationStatus
 */
public class EMUpdateApplicationStatus extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession sess = req.getSession();
		int appID = Integer.parseInt(req.getParameter("appID"));
		String updateStatus = req.getParameter("updateStatus");
		@SuppressWarnings("unchecked")
		List<JobApplication> apps = (List<JobApplication>) sess.getAttribute("filtered-apps");
		if(apps == null || apps.isEmpty()) {
			sess.setAttribute("outcome", "Failed to update application status.");
			sess.setAttribute("outcome-read", Boolean.parseBoolean("false"));
			sess.setAttribute("success-failure", "failure");
			resp.sendRedirect("/job-portal/employer/employerDashboard.jsp?currentView=view-applications");
		}
		
		
		EmployerCompany ec = (EmployerCompany)sess.getAttribute("logged-in-employer");
		if(ec.updateApplicationStatus(appID, updateStatus)) {
			sess.setAttribute("outcome", "Application status updated successfully!");
			sess.setAttribute("outcome-read", Boolean.parseBoolean("false"));
			sess.setAttribute("success-failure", "success");
			for(JobApplication app : apps) {
				if(app.getId() == appID) {
					app.setStatus(ApplicationStatus.valueOf(updateStatus));
					sess.setAttribute("filtered-apps", apps);
					break;
				}
			}
			resp.sendRedirect("/job-portal/employer/employerDashboard.jsp?currentView=view-applications");
		}
		else {
			sess.setAttribute("outcome", "Failed to update application status.");
			sess.setAttribute("outcome-read", Boolean.parseBoolean("false"));
			sess.setAttribute("success-failure", "failure");
			resp.sendRedirect("/job-portal/employer/employerDashboard.jsp?currentView=view-applications");
		}
	}

}
