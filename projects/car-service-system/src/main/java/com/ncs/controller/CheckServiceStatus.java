package com.ncs.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.ncs.model.CarModel;

public class CheckServiceStatus extends HttpServlet {
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sess = request.getSession();
		int user_id = (int) sess.getAttribute("user_id");
		
		CarModel model = new CarModel();
		ArrayList<CarModel> cars = model.viewCarDetails(user_id);
		
		sess.setAttribute("cars", cars);
		
		if(cars.isEmpty()) {
			sess.setAttribute("outcome", "No cars found.");
			response.sendRedirect("/CarServiceSystem/customer-links/loggedInMenu.jsp");
		} else {
			response.sendRedirect("/CarServiceSystem/car-links/checkServiceStatus.jsp");
		}
	}
}
