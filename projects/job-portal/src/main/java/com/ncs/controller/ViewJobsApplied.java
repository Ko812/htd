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
 * Servlet implementation class ViewJobsApplied
 */
public class ViewJobsApplied extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession sess = req.getSession();
		JobSeeker js = (JobSeeker) sess.getAttribute("logged-in-job-seeker");
		sess.setAttribute("applications" , js.pullJobsApplied());
		sess.setAttribute("flow", null);
		resp.sendRedirect("/job-portal/seeker/jobSeekerDashboard.jsp?currentView=view-jobs-applied");
	}

}
