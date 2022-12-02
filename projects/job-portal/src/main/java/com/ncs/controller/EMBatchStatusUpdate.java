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
 * Servlet implementation class EMBatchStatusUpdate
 */
public class EMBatchStatusUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession sess = req.getSession();
		EmployerCompany ec = (EmployerCompany)sess.getAttribute("logged-in-employer");
		String selectAll = req.getParameter("selectAll");
		List<JobApplication> selectedApps = new ArrayList<JobApplication>();
		@SuppressWarnings("unchecked")
		List<JobApplication> filteredApps = (List<JobApplication>) sess.getAttribute("filtered-apps");
		if(selectAll != null && selectAll.equals("selectAll")) {
			for(JobApplication app : filteredApps) {
				String appStatus = req.getParameter("appStatus"+app.getId());
				app.setChecked(true);
				app.setStatus(ApplicationStatus.valueOf(appStatus));
				selectedApps.add(app);
			}
		}
		else {
			for(JobApplication app : filteredApps) {
				String appSelection = req.getParameter("checkbox"+app.getId());
				if(appSelection != null) {
					String appStatus = req.getParameter("appStatus"+app.getId());
					app.setChecked(true);
					app.setStatus(ApplicationStatus.valueOf(appStatus));
					selectedApps.add(app);
				}
			}
		}
		if(ec.batchUpdateApplicationStatus(selectedApps)) {
			sess.setAttribute("outcome", "Batch applications status updated successfully!");
			sess.setAttribute("outcome-read", Boolean.parseBoolean("false"));
			sess.setAttribute("success-failure", "success");
			resp.sendRedirect("/job-portal/ViewJobApplications");
		}
		else {
			sess.setAttribute("outcome", "Failed to update batch application status.");
			sess.setAttribute("outcome-read", Boolean.parseBoolean("false"));
			sess.setAttribute("success-failure", "failure");
			resp.sendRedirect("/job-portal/employer/employerDashboard.jsp?currentView=view-applications");
		}
	}

}
