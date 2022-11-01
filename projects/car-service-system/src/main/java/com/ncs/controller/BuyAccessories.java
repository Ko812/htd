package com.ncs.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ncs.model.CarAccessory;

public class BuyAccessories extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession sess = req.getSession();
		@SuppressWarnings("unchecked")
		ArrayList<CarAccessory> orders = (ArrayList<CarAccessory>) sess.getAttribute("items");
		if(!orders.isEmpty()) {
			int outcome = CarAccessory.sendOrder(orders);
			if(outcome > 0) {
				sess.setAttribute("outcome", "Order sent.");
			}
			else {
				sess.setAttribute("outcome", "No order sent.");
			}
		}
		else {
			sess.setAttribute("outcome", "No order made");
		}
		resp.sendRedirect("/CarServiceSystem/customer-links/loggedInMenu.jsp");
		
	}
}
