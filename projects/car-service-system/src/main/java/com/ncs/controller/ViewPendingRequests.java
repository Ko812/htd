package com.ncs.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ncs.model.CarModel;

/**
 * Servlet implementation class ViewPendingRequests
 */
public class ViewPendingRequests extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<CarModel> serviceRequests = CarModel.viewAllCarServiceRequests();
		HttpSession sess = request.getSession();
		sess.setAttribute("serviceRequests", serviceRequests);
		response.sendRedirect("/CarServiceSystem/admin-links/viewPendingServiceRequests.jsp");
	}
}
