package com.ncs.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ncs.model.CarDetails;
import com.ncs.model.CarModel;


public class ViewCarDetails extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession sess = request.getSession();
		
		int user_id = (int) sess.getAttribute("user_id");
		CarDetails car = new CarDetails();
		
		ArrayList<CarDetails> carDetails = car.viewCarDetails(user_id);
		
		if(carDetails.isEmpty()) {
			sess.setAttribute("outcome", "No cars found.");
			response.sendRedirect("/CarServiceSystem/customer-links/loggedInMenu.jsp");
		} else {
			sess.setAttribute("carDetails", carDetails);
			response.sendRedirect("/CarServiceSystem/car-links/viewCarDetails.jsp");
		}
		
	}
}
