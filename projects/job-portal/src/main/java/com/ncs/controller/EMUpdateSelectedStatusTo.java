package com.ncs.controller;

import java.io.IOException;
import java.util.ArrayList;
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
 * Servlet implementation class EMUpdateSelectedStatusTo
 */
public class EMUpdateSelectedStatusTo extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession sess = req.getSession();
		EmployerCompany ec = (EmployerCompany)sess.getAttribute("logged-in-employer");
		ApplicationStatus appStatus = ApplicationStatus.valueOf(req.getParameter("appStatus"));
		String selectAll = req.getParameter("selectAll");
		List<JobApplication> selectedApps = new ArrayList<JobApplication>();
		
		@SuppressWarnings("unchecked")
		List<JobApplication> filteredApps = (List<JobApplication>) sess.getAttribute("filtered-apps");
		if(selectAll != null && selectAll.equals("selectAll")) {
			for(JobApplication app : filteredApps) {
				ApplicationStatus stat = app.getStatus();
				app.setChecked(true);
				app.setStatus(appStatus);
				selectedApps.add(app);
			}
		}
		else {
			int counter = 0;
			String appId = req.getParameter("app"+counter);
			System.out.println("received " +appId);
			while(appId != null) {
				int appIdInt = Integer.parseInt(appId);
				for(JobApplication app : filteredApps) {
					if(appIdInt == app.getId()) {
						app.setChecked(true);
						app.setStatus(appStatus);
						selectedApps.add(app);
						break;
					}
				}
				counter++;
				appId = req.getParameter("app"+counter);
			}
		}
		
		if(ec.updateSelectedAppsStatusTo(appStatus, selectedApps)) {
			sess.setAttribute("outcome", "All applications status updated!");
			sess.setAttribute("outcome-read", Boolean.parseBoolean("false"));
			sess.setAttribute("success-failure", "success");
			resp.sendRedirect("/job-portal/ViewJobApplications");
		}
		else {
			sess.setAttribute("outcome", "Failed to update application status.");
			sess.setAttribute("outcome-read", Boolean.parseBoolean("false"));
			sess.setAttribute("success-failure", "failure");
			resp.sendRedirect("/job-portal/employer/employerDashboard.jsp?currentView=view-applications");
		}
	}

}
