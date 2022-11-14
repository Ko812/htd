package com.ncs.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ncs.model.EmployerCompany;
import com.ncs.model.JobSeeker;

/**
 * Servlet implementation class JSRegister
 */
public class JSRegister extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		String userName = req.getParameter("userName");
		String password = req.getParameter("password");
		String email = req.getParameter("email");
		String NRIC = req.getParameter("NRIC");
		String contact = req.getParameter("contact");
		HttpSession sess = req.getSession();
		
		JobSeeker js = new JobSeeker(firstName, lastName, userName, NRIC, contact, email);
		if(js.register(password, sess)) {
			resp.sendRedirect("/job-portal/login.jsp");
		}
		else {
			resp.sendRedirect("/job-portal/seeker/register.jsp");
		}
	}

}
