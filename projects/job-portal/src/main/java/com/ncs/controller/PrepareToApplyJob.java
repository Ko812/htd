package com.ncs.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ncs.model.Job;

/**
 * Servlet implementation class PrepareToApplyJob
 */
public class PrepareToApplyJob extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession sess = req.getSession();
		
		@SuppressWarnings("unchecked")
		List<Job> jobs = (List<Job>) sess.getAttribute("searchResults");
		int jobID = Integer.parseInt(req.getParameter("jobID"));
		for(Job job : jobs) {
			if(job.getId() == jobID) {
				sess.setAttribute("job-to-apply", job);
				sess.setAttribute("flow", "job-application-flow");
				resp.sendRedirect("/job-portal/seeker/jobSeekerDashboard.jsp?currentView=apply-job");
				return;
			}
		}
		resp.sendRedirect("/job-portal/seeker/jobSeekerDashboard.jsp");
		
	}

}
