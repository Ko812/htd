package com.ncs.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ncs.model.Model;


public class Register extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String username = request.getParameter("userName");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		
		Model m = new Model(name, username, password, email);
		int outcome = m.register();
		
		HttpSession sess = request.getSession(true);
		sess.setAttribute("name", name);
		
		if(outcome == -1) {
			sess.setAttribute("register-form-message", "User name already exists. Please select a different user name.");
			response.sendRedirect("/CarServiceSystem/customer-links/registerCustomer.jsp");
		}
		else if(outcome == 1) {
			sess.setAttribute("outcome", "User created");
			response.sendRedirect("/CarServiceSystem/index.jsp");
		}
		else {
			sess.setAttribute("register-form-message", "Failed to create new user. Try again later.");
			response.sendRedirect("/CarServiceSystem/customer-links/registerCustomer.jsp");
		}
	}

}