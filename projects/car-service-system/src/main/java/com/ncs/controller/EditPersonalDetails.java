package com.ncs.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ncs.model.Model;

public class EditPersonalDetails extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession sess = req.getSession();
		
		String userType = (String) sess.getAttribute("userType");
		int user_id = (int) sess.getAttribute("user_id");
		Model customer = new Model();
		customer.viewCustomer(user_id);
		sess.setAttribute("customer_name", customer.getName());
		sess.setAttribute("customer_username", customer.getUsername());
		sess.setAttribute("customer_email", customer.getEmail());
		resp.sendRedirect("/CarServiceSystem/customer-links/editPersonalDetails.jsp");
	}
}
