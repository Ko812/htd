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


public class DeleteJobPosting extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession sess = req.getSession();
		EmployerCompany ec = (EmployerCompany) sess.getAttribute("logged-in-employer");
		int jobID = Integer.parseInt(req.getParameter("jobID"));
		int outcome = ec.closeJobPosting(jobID);
		if(outcome == 1) {
			sess.setAttribute("outcome", "Job posting closed successfully.");
			sess.setAttribute("outcome-read", Boolean.parseBoolean("false"));
			sess.setAttribute("success-failure", "success");
			resp.sendRedirect("/job-portal/ViewJobPostings");
		}
		else {
			sess.setAttribute("outcome", "Job posting closure unsuccessful.");
			sess.setAttribute("outcome-read", Boolean.parseBoolean("false"));
			sess.setAttribute("success-failure", "failure");
			resp.sendRedirect("/job-portal/employer/employerDashboard.jsp");
		}
	}
}
