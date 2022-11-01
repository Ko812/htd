package com.ncs.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ncs.model.CarModel;

public class AddCarDetails extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sess = request.getSession();
		
		int user_id = (int) sess.getAttribute("user_id");
		String carModel = request.getParameter("carModel");
		String carType = request.getParameter("carType");
		String carRegistrationNumber = request.getParameter("carRegistrationNumber");
		
		CarModel car = new CarModel(user_id, carModel, carType, carRegistrationNumber, "false", "false");
		int outcome = car.addCarDetails();
		
		if(outcome == 1) {
			sess.setAttribute("outcome", "Car details added.");
		} 
		else if (outcome == -2) {
			sess.setAttribute("add-car-form-message", "Car with registration number " + carRegistrationNumber + " already exists.");
			response.sendRedirect("/CarServiceSystem/car-links/addCarDetails.jsp");
		}
		else {
			sess.setAttribute("add-car-form-message", "Failed to add car details.");
			response.sendRedirect("/CarServiceSystem/car-links/addCarDetails.jsp");
		}
		response.sendRedirect("/CarServiceSystem/customer-links/loggedInMenu.jsp");
	}

}
