package com.ncs.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ncs.model.Model;

public class EditCustomerDetails extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession sess = req.getSession();
		String username = req.getParameter("username");
		
		Model customer = new Model();
		customer.viewCustomer(username);
		
		sess.setAttribute("customerID", customer.customer_id);
		sess.setAttribute("customerName", customer.getName());
		sess.setAttribute("customerUsername", username);
		sess.setAttribute("customerEmail", customer.getEmail());

		resp.sendRedirect("/CarServiceSystem/admin-links/editCustomerDetails.jsp");
	}
}
