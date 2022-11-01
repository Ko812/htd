package com.ncs.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ncs.model.CarModel;
import com.ncs.model.Model;

public class UpdatePersonalDetails extends HttpServlet {
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession sess = request.getSession();
		
		String userType = (String) sess.getAttribute("userType");
		String username = (String) sess.getAttribute("username"); 
		String newUsername = request.getParameter("userName");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		
		Model customer = new Model();
		customer.setUsername(newUsername);
		customer.setEmail(email);
		customer.setName(name);
		
		int outcome = customer.updateDetails(username);
		if(outcome >= 1) {
			sess.setAttribute("username", newUsername);
			sess.setAttribute("outcome", "User details updated.");
		}
		else if(outcome == -2){
			sess.setAttribute("edit-customer-form-message", "User name is already in used");
			resp.sendRedirect("/CarServiceSystem/admin-links/editPersonalDetails.jsp");
		}
		else if(outcome == 0){
			sess.setAttribute("outcome", "Personal details not updated.");
		}
		
		if(userType.equals("admin")) {
			resp.sendRedirect("/CarServiceSystem/admin-links/adminMenu.jsp");
		}
		else if (userType.equals("customer")) {
			resp.sendRedirect("/CarServiceSystem/customer-links/loggedInMenu.jsp");
		}
	}
}
