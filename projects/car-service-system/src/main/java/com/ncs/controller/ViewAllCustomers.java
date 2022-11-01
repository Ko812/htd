package com.ncs.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ncs.model.Model;


public class ViewAllCustomers extends HttpServlet {
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<Model> allCustomers = Model.viewAllCustomers();
		HttpSession sess = request.getSession();
		
		sess.setAttribute("allCustomers", allCustomers);
		response.sendRedirect("/CarServiceSystem/admin-links/viewAllCustomers.jsp");
	}

}
