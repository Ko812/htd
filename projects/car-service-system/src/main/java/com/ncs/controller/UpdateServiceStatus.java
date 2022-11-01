package com.ncs.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ncs.model.CarModel;

public class UpdateServiceStatus extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession sess = req.getSession();
		int customer_id = (int) sess.getAttribute("customer_id");
		String registrationNumber = (String) sess.getAttribute("customerCarRegNum");
		String serviceStatus = req.getParameter("serviceStatus");
		
		CarModel car = new CarModel(customer_id, "", "", registrationNumber, "", serviceStatus);
		
		int outcome = car.updateServiceStatus();
		if(outcome == 1) {
			sess.setAttribute("outcome", "Service status updated");
		}
		else {
			sess.setAttribute("outcome", "Service status update failed");
		}
		resp.sendRedirect("/CarServiceSystem/admin-links/adminMenu.jsp");
	}
}
