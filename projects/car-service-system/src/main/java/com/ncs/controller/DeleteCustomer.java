package com.ncs.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ncs.model.Model;

public class DeleteCustomer extends HttpServlet {
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		Model customer = new Model();
		customer.setUsername(username);
		customer.deleteAccount();
		HttpSession sess = req.getSession();
		sess.setAttribute("outcome", "Customer " + username + " is deleted");
		
		resp.sendRedirect("/CarServiceSystem/admin-links/adminMenu.jsp");
	}

}
