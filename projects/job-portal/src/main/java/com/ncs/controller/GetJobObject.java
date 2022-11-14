package com.ncs.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ncs.model.EmployerCompany;
import com.ncs.model.Job;

/**
 * Servlet implementation class GetJobObject
 */
public class GetJobObject extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession sess = req.getSession();
		
		@SuppressWarnings("unchecked")
		List<Job> jobs = (List<Job>) sess.getAttribute("jobs");
		int jobID = Integer.parseInt(req.getParameter("jobID"));
		for(Job job : jobs) {
			if(job.getId() == jobID) {
				sess.setAttribute("job-to-edit", job);
				resp.sendRedirect("/job-portal/employer/employerDashboard.jsp?currentView=edit-job-posting");
				return;
			}
		}
		resp.sendRedirect("/job-portal/employer/employDashboard.jsp?currentView=view-job-postings");
		
	}
}
