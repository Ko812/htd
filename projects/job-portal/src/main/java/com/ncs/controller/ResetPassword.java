package com.ncs.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ncs.model.DBOps;

public class ResetPassword extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession sess = req.getSession();
		String loginAs = (String) sess.getAttribute("loginAs");
		String email = (String) sess.getAttribute("reset-for-email");
		String newPassword = req.getParameter("newPassword");
		String cfmPassword = req.getParameter("cfmPassword");
		String sessionCode = req.getParameter("sessionCode");
		
		if(newPassword.equals(cfmPassword)) {
			int out = (new DBOps()).resetPassword(cfmPassword, loginAs, email, sessionCode);
			if(out == 1) {
				sess.setAttribute("login-form-message", "Password change success! Login to continue.");
				sess.setAttribute("form-message-read", Boolean.parseBoolean("false"));
			}
			else {
				sess.setAttribute("login-form-message", "Password change failed. Try again later.");
				sess.setAttribute("form-message-read", Boolean.parseBoolean("false"));
			}
			resp.sendRedirect("/job-portal/login.jsp");
		}
		else {
			sess.setAttribute("reset-password-form-message", "Password mismatch.");
			sess.setAttribute("form-message-read", Boolean.parseBoolean("false"));
			resp.sendRedirect("/job-portal/resetPassword.jsp");
		}
	}

}
