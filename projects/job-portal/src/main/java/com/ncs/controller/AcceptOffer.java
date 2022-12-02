package com.ncs.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ncs.model.JobSeeker;

/**
 * Servlet implementation class AcceptOffer
 */
public class AcceptOffer extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession sess = req.getSession();
		int appID = Integer.parseInt(req.getParameter("appID"));
		JobSeeker js = (JobSeeker)sess.getAttribute("logged-in-job-seeker");
		if(js.acceptJobOffer(appID)) {
			sess.setAttribute("outcome", "Job accepted!");
			sess.setAttribute("success-failure", "success");
			sess.setAttribute("outcome-read", Boolean.parseBoolean("false"));
			resp.sendRedirect("/job-portal/ViewJobsApplied");
		}
		else {
			sess.setAttribute("outcome", "Failed to accept job offer.");
			sess.setAttribute("success-failure", "failure");
			sess.setAttribute("outcome-read", Boolean.parseBoolean("false"));
			resp.sendRedirect("/job-portal/seeker/jobSeekerDashboard.jsp?currentView=view-jobs-applied");
		}
	}
}
