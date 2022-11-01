package com.ncs.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ncs.model.CarDetails;

public class EditCarDetails extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sess = request.getSession();
		int user_id = (int) sess.getAttribute("user_id");
		String registrationNumber = request.getParameter("carRegistrationNumber");
		
		CarDetails model = new CarDetails();
		ArrayList<CarDetails> carDetails = model.viewCarDetails(user_id);
		
		if(carDetails.isEmpty()) {
			sess.setAttribute("outcome", "No car in the record.");
			response.sendRedirect("/CarServiceSystem/customer-links/loggedInMenu.jsp");
		}
		else {
			for(CarDetails car : carDetails) {
				if(car.getRegistrationNumber().equals(registrationNumber)) {
					sess.setAttribute("reg_num", registrationNumber);
					sess.setAttribute("car_model", car.getModel());
					sess.setAttribute("car_type", car.getType());
				}
			}
			response.sendRedirect("/CarServiceSystem/car-links/editCarDetails.jsp");
		}
	}
}
