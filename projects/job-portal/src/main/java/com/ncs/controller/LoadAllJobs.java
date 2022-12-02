package com.ncs.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ncs.model.DBOps;
import com.ncs.model.Job;
import com.ncs.model.JobSeeker;
import com.ncs.model.SearchUtility;


public class LoadAllJobs extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession sess = req.getSession();
		JobSeeker js = (JobSeeker) sess.getAttribute("logged-in-job-seeker");
		String callingFrom = req.getParameter("callingFrom");
		List<Job> allJobs = js.db.loadAllJobs();
		sess.setAttribute("all-jobs", allJobs);
		if(callingFrom.equals("home")) {
			resp.sendRedirect("/job-portal/index.jsp");
		}
		else if(callingFrom.equals("job-seeker-search-page")) {
			resp.sendRedirect("/job-portal/seeker/jobSeekerDashboard.jsp?currentView=search-jobs");
		}
		
	}

}
