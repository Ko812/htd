package com.ncs.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ncs.model.AdminModel;

/**
 * Servlet implementation class AdminLogin
 */
public class AdminLogin extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("userName");
		String password = request.getParameter("password");
		
		AdminModel m = new AdminModel();
		int respCode = m.login(username, password);
		
		HttpSession sess = request.getSession(true);
		if(respCode == 1) {
			sess.setAttribute("outcome", "You have successfully logged in.");
			sess.setAttribute("username", username);
			sess.setAttribute("userType", "admin");
			response.sendRedirect("/CarServiceSystem/admin-links/adminMenu.jsp");
		}
		else if(respCode == -2 || respCode == -3){
			sess.setAttribute("login-form-message", "Invalid user name or password.");
			response.sendRedirect("/CarServiceSystem/admin-links/adminLogin.jsp");
		}
		else {
			sess.setAttribute("login-form-message", "Admin login failed.");
			response.sendRedirect("/CarServiceSystem/admin-links/adminLogin.jsp");
		}
	}
}
