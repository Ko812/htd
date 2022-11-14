package com.ncs.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ncs.model.EmployerCompany;

/**
 * Servlet implementation class EMRegister
 */
public class EMRegister extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String companyName = req.getParameter("companyName");
		String userName = req.getParameter("userName");
		String password = req.getParameter("password");
		String companyEmail = req.getParameter("companyEmail");
		HttpSession sess = req.getSession(true);
		
		EmployerCompany ec = new EmployerCompany(companyName, userName, companyEmail);
		if(ec.register(password, sess)) {
			resp.sendRedirect("/job-portal/login.jsp");
		}
		else {
			resp.sendRedirect("/job-portal/employer/register.jsp");
		}
	}

}
