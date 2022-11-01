package com.ncs.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ncs.model.Model;

public class UpdateCustomerDetails extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession sess = req.getSession();
		
		String userType = (String) sess.getAttribute("userType");
		String oldUsername = (String) sess.getAttribute("customerUsername"); 
		String newUsername = req.getParameter("customerUsername");
		String name = req.getParameter("customerName");
		String email = req.getParameter("customerEmail");
		
		Model customer = new Model();
		customer.setUsername(newUsername);
		customer.setEmail(email);
		customer.setName(name);

		int outcome = customer.updateDetails(oldUsername);
		
		if(outcome >= 1) {
			sess.setAttribute("outcome", "User details updated.");
		} 
		else if(outcome == -2){
			sess.setAttribute("edit-customer-form-message", "User name is already in used");
			resp.sendRedirect("/CarServiceSystem/admin-links/editCustomerDetails.jsp");
		}
		else if(outcome == 0){
			sess.setAttribute("outcome", "User details not updated.");
		}
		if(userType.equals("admin")) {
			resp.sendRedirect("/CarServiceSystem/admin-links/adminMenu.jsp");
		}
		else if (userType.equals("customer")) {
			resp.sendRedirect("/CarServiceSystem/customer-links/loggedInMenu.jsp");
		}
	}
}
