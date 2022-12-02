package com.ncs.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ncs.model.EmployerCompany;
import com.ncs.model.JobApplication;
import com.ncs.model.JobSeeker;

/**
 * Servlet implementation class ViewSingleApplication
 */
public class ViewSingleApplication extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession sess = req.getSession();
		EmployerCompany ec = (EmployerCompany) sess.getAttribute("logged-in-employer");
		int appID = Integer.parseInt(req.getParameter("appID"));
		JobApplication app = null;
		
		@SuppressWarnings("unchecked")
		List<JobApplication> allJobsApplied = (List<JobApplication>) sess.getAttribute("all-applications");
		if(allJobsApplied == null) {
			allJobsApplied = ec.getAllApplications();
			sess.setAttribute("all-applications", allJobsApplied);
		}
		for(JobApplication a : allJobsApplied) {
			if(a.getId() == appID) {
				app = a;
				break;
			}
		}
		if(app != null) {
			sess.setAttribute("app-to-view", app);
			resp.sendRedirect("/job-portal/employer/employerDashboard.jsp?currentView=view-single-application");
		}
		else {
			sess.setAttribute("outcome", "Application loading error.");
			sess.setAttribute("outcome-read", Boolean.parseBoolean("false"));
			sess.setAttribute("success-failure", "failure");
			resp.sendRedirect("/job-portal/employer/employerDashboard.jsp?currentView=view-applications");
		}
	}

}
