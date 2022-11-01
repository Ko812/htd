package com.ncs.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ncs.model.Model;

public class ResetPassword extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String oldPassword = request.getParameter("oldPassword");
		String newPassword = request.getParameter("newPassword");
		String cfmPassword = request.getParameter("cfmPassword");
		
		if(!newPassword.equals(cfmPassword)) {
			response.sendRedirect("/CarServiceSystem/customer-links/resetPasswordFailed.html");
			return;
		}
		
		HttpSession sess = request.getSession();
		int user_id = (int) sess.getAttribute("user_id");
		
		Model m = new Model();
		int outcome = m.resetPassword(user_id, oldPassword, newPassword, cfmPassword);
		
		if(outcome == 1) {
			sess.setAttribute("outcome", "Password reset success");
			response.sendRedirect("/CarServiceSystem/customer-links/loggedInMenu.jsp");
		}
		else if (outcome == -3){
			sess.setAttribute("reset-password-form-message", "Wrong password");
			response.sendRedirect("/CarServiceSystem/customer-links/resetPassword.jsp");
		}
		else if (outcome == -2){
			sess.setAttribute("reset-password-form-message", "Invalid user. Please re-login to change password.");
			response.sendRedirect("/CarServiceSystem/customer-links/resetPassword.jsp");
		}
	}

}
