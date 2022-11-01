package com.ncs.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ncs.model.CarModel;


public class RequestService extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sess = request.getSession();
		
		int user_id = (int) sess.getAttribute("user_id");
		String carRegistrationNumber = request.getParameter("carRegistrationNumber");
		String serviceRequest = request.getParameter("serviceRequest");
		
		CarModel car = new CarModel(user_id, "", "", carRegistrationNumber, serviceRequest, "confirmed");
	
		int outcome = car.requestService();	
		System.out.println("request: " +outcome);
		if(outcome == 1) {
			sess.setAttribute("outcome", "Service request submitted.");
		} 
		else if (outcome == -2) {
			sess.setAttribute("outcome", "Car already under servicing.");
		}
		else {
			sess.setAttribute("outcome", "Service request failed.");
		}
		response.sendRedirect("/CarServiceSystem/customer-links/loggedInMenu.jsp");
	}
}
