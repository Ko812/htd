package com.ncs.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ncs.model.CarModel;


public class DeleteCar extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession sess = req.getSession();
		int user_id = (int) sess.getAttribute("user_id");
		String registrationNumber = req.getParameter("carRegistrationNumber");
		
		CarModel car = new CarModel(user_id, "", "", registrationNumber, "", "");
		int outcome = car.deleteCar();
		
		if(outcome == 1) {
			sess.setAttribute("outcome", "Car record deleted.");
		}
		else {
			sess.setAttribute("outcome", "Deleting car record failed.");
		}
		resp.sendRedirect("/CarServiceSystem/customer-links/loggedInMenu.jsp");
	}
}
