package com.ncs.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ncs.model.Model;

public class DeleteAccount extends HttpServlet {
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession sess = req.getSession();
		String username = (String) sess.getAttribute("username");
		
		Model customer = new Model();
		customer.setUsername(username);
		customer.deleteAccount();
		resp.sendRedirect("/CarServiceSystem/index.html");
	}
}
