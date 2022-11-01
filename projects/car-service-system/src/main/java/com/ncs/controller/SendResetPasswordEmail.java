package com.ncs.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ncs.model.Model;


public class SendResetPasswordEmail extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String userEmail = req.getParameter("userEmail");
		HttpSession sess = req.getSession();
		
		Model cust = new Model();
		if(!cust.userEmailExists(userEmail)) {
			sess.setAttribute("send-reset-password-form-message", "User not found.");
			resp.sendRedirect("/CarServiceSystem/customer-links/forgetPassword.jsp");
		}
		else if(userEmail.isBlank()) {
			sess.setAttribute("send-reset-password-form-message", "Email cannot be blank.");
			resp.sendRedirect("/CarServiceSystem/customer-links/forgetPassword.jsp");
		}
		else if(!userEmail.contains("@")) {
			sess.setAttribute("send-reset-password-form-message", "Invalid email address.");
			resp.sendRedirect("/CarServiceSystem/customer-links/forgetPassword.jsp");
		}
		else {
			sess.setAttribute("send-reset-password-form-message", null);
			sess.setAttribute("userEmail", userEmail);
			resp.sendRedirect("/CarServiceSystem/customer-links/sendResetPasswordEmailResult.jsp");
		}
		
	}
}
