package com.ncs.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ncs.model.AdminModel;
import com.ncs.model.Model;


public class Login extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("userName");
		String password = request.getParameter("password");
	
		Integer user_id;
		
		HttpSession sess = request.getSession(true);
		
		Model m = new Model();
		user_id = m.login(username, password);
		
		if(user_id > 0) {
			sess.setAttribute("outcome", "You have successfully logged in.");
			sess.setAttribute("username", username);
			sess.setAttribute("user_id", user_id);
			sess.setAttribute("userType", "customer");
			response.sendRedirect("/CarServiceSystem/customer-links/loggedInMenu.jsp");
		}
		else if(user_id == -3 || user_id == -2) {
			sess.setAttribute("login-form-message", "Invalid user name or password.");
			response.sendRedirect("/CarServiceSystem/customer-links/customerLogin.jsp");
		}
		else {
			sess.setAttribute("login-form-message", "Login failed.");
			response.sendRedirect("/CarServiceSystem/customer-links/customerLogin.jsp");
		}
	}
}
