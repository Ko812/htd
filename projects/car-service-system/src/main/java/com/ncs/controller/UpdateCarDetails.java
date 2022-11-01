package com.ncs.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ncs.model.CarModel;

public class UpdateCarDetails extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sess = request.getSession();
		int user_id = (int) sess.getAttribute("user_id");
		String originalCarRegistrationNumber = (String) sess.getAttribute("reg_num");
		String carRegistrationNumber = request.getParameter("carRegistrationNumber");
		String carModel = request.getParameter("carModel");
		String carType = request.getParameter("carType");
		
		CarModel car = new CarModel(user_id, carModel, carType, carRegistrationNumber, "false", "false");
		
		int outcome = car.updateCarDetails(originalCarRegistrationNumber);
		if(outcome == 1) {
			sess.setAttribute("outcome", "Car details updated");
			response.sendRedirect("/CarServiceSystem/customer-links/loggedInMenu.jsp");
		}
		else {
			sess.setAttribute("outcome", "Car details update failed.");
			response.sendRedirect("/CarServiceSystem/customer-links/loggedInMenu.jsp");
		}
	}
}
