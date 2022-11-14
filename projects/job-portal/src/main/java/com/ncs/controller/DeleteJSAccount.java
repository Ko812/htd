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
import com.ncs.model.JobSeeker;

/**
 * Servlet implementation class DeleteJSAccount
 */
public class DeleteJSAccount extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession sess = req.getSession();
		JobSeeker js = (JobSeeker) sess.getAttribute("logged-in-job-seeker");
		
		if(js.delete()) {
			sess.setAttribute("outcome", "Account deleted");
			sess.setAttribute("outcome-read", Boolean.parseBoolean("false"));
			resp.sendRedirect("/job-portal/EMLogout");
		}
		else {
			sess.setAttribute("outcome", "Account delete failed. Try again later.");
			sess.setAttribute("outcome-read", Boolean.parseBoolean("false"));
			resp.sendRedirect("/job-portal/seeker/jobSeekerDashboard.jsp");
		}
	}

}
