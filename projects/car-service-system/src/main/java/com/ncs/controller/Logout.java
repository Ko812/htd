package com.ncs.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Logout
 */
public class Logout extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession sess = req.getSession();
		sess.setAttribute("outcome", "You have logged out");
		sess.setAttribute("username", null);
		sess.setAttribute("user_id", null);
		sess.setAttribute("form-message", null);
		sess.setAttribute("login-form-message", null);
		sess.setAttribute("reset-password-form-message", null);
		sess.setAttribute("register-form-message", null);
		sess.setAttribute("service-request-form-message", null);
		sess.setAttribute("edit-car-form-message", null);
		resp.sendRedirect("/CarServiceSystem/index.jsp");
	}

}
