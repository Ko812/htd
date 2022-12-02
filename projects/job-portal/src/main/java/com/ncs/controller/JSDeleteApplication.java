package com.ncs.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ncs.model.JobApplication;
import com.ncs.model.JobSeeker;

/**
 * Servlet implementation class JSDeleteApplication
 */
public class JSDeleteApplication extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession sess = req.getSession();
		int appID = Integer.parseInt(req.getParameter("appID"));
		JobSeeker js = (JobSeeker)sess.getAttribute("logged-in-job-seeker");
		if(js.cancelJobApplication(appID)) {
			sess.setAttribute("outcome", "Application canceled successfully!");
			sess.setAttribute("outcome-read", Boolean.parseBoolean("false"));
			sess.setAttribute("success-failure", "success");
			resp.sendRedirect("/job-portal/ViewJobsApplied");
		}
		else {
			sess.setAttribute("outcome", "Job application cancellation unsuccessful.");
			sess.setAttribute("outcome-read", Boolean.parseBoolean("false"));
			sess.setAttribute("success-failure", "failure");
			resp.sendRedirect("/job-portal/seeker/jobSeekerDashboard.jsp?currentView=view-jobs-applied");
		}
	}

}
