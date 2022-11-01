package com.ncs.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ncs.model.CarModel;

public class RedirectServiceStatus extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int user_id = Integer.parseInt(req.getParameter("customerID"));
		String registrationNumber = req.getParameter("regnum");
		String serviceStatus = req.getParameter("status");
		String serviceRequest = req.getParameter("request");
		CarModel car = new CarModel(user_id, "", "", registrationNumber, serviceRequest, serviceStatus);
		HttpSession sess = req.getSession();
		sess.setAttribute("customer_id", user_id);
		sess.setAttribute("customerCarRegNum", registrationNumber);
		sess.setAttribute("customerServiceRequest", serviceRequest);
		sess.setAttribute("customerServiceStatus", serviceStatus);
		resp.sendRedirect("/CarServiceSystem/admin-links/updateServiceStatus.jsp");
	}

}
