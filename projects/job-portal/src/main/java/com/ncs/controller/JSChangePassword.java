package com.ncs.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ncs.model.JobSeeker;

/**
 * Servlet implementation class JSChangePassword
 */
public class JSChangePassword extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession sess = req.getSession();
		String oldPassword = req.getParameter("oldPassword");
		String newPassword = req.getParameter("newPassword");
		String cfmPassword = req.getParameter("cfmPassword");
		
		if(!newPassword.equals(cfmPassword)) {
			sess.setAttribute("change-password-form-message", "Passwords mismatch.");
			sess.setAttribute("form-message-read", Boolean.parseBoolean("false"));
			resp.sendRedirect("/job-portal/seeker/jobSeekerDashboard.jsp?currentView=change-password");
			return;
		}
		JobSeeker js = (JobSeeker) sess.getAttribute("logged-in-job-seeker");
		if(js.changePassword(oldPassword, cfmPassword, sess)) {
			sess.setAttribute("outcome", "Password changed successfully.");
			sess.setAttribute("outcome-read", Boolean.parseBoolean("false"));
			sess.setAttribute("success-failure", "success");
			resp.sendRedirect("/job-portal/seeker/jobSeekerDashboard.jsp");
		}
		else {
			resp.sendRedirect("/job-portal/seeker/jobSeekerDashboard.jsp?currentView=change-password");
		}
	}

}
