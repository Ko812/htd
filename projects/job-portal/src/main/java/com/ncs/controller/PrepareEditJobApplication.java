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
 * Servlet implementation class PrepareEditJobApplication
 */
public class PrepareEditJobApplication extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession sess = req.getSession();
		JobSeeker js = (JobSeeker) sess.getAttribute("logged-in-job-seeker");
		int appID = Integer.parseInt(req.getParameter("appID"));
		JobApplication app = null;
		
		@SuppressWarnings("unchecked")
		List<JobApplication> allJobsApplied = (List<JobApplication>) sess.getAttribute("applications");
		for(JobApplication a : allJobsApplied) {
			if(a.getId() == appID) {
				app = a;
				break;
			}
		}
		if(app != null) {
			sess.setAttribute("app-to-edit", app);
			sess.setAttribute("flow", "job-application-edit-flow");
			resp.sendRedirect("/job-portal/seeker/jobSeekerDashboard.jsp?currentView=edit-job-application");
		}
		else {
			sess.setAttribute("outcome", "Error. Application not found");
			sess.setAttribute("outcome-read", Boolean.parseBoolean("false"));
			sess.setAttribute("success-failure", "success");
			resp.sendRedirect("/job-portal/seeker/jobSeekerDashboard.jsp?currentView=view-jobs-applied");
		}
	}

}
