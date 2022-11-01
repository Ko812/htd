package com.ncs.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ncs.model.CarAccessory;


public class CheckoutItems extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int decalQuantity = Integer.parseInt(req.getParameter("decalQuantity"));
		int carHookQuantity = Integer.parseInt(req.getParameter("carHookQuantity"));
		int handphoneHolderQuantity = Integer.parseInt(req.getParameter("handphoneHolderQuantity"));
		int airRefreshenerQuantity = Integer.parseInt(req.getParameter("airRefreshenerQuantity"));
		int frontRearCameraQuantity = Integer.parseInt(req.getParameter("frontRearCameraQuantity"));
		
		HttpSession sess = req.getSession();
		int user_id = (int) sess.getAttribute("user_id");
		ArrayList<CarAccessory> items = new ArrayList<CarAccessory>();
		if(decalQuantity != 0) {
			items.add(new CarAccessory("Decal", 100f, decalQuantity, user_id));
		}
		if(carHookQuantity != 0) {
			items.add(new CarAccessory("Car hook", 15f, carHookQuantity, user_id));
		}
		if(handphoneHolderQuantity != 0) {
			items.add(new CarAccessory("Handphone holder", 35f, handphoneHolderQuantity, user_id));
		}
		if(airRefreshenerQuantity != 0) {
			items.add(new CarAccessory("Air Freshener (x2)", 100f, airRefreshenerQuantity, user_id));
		}
		if(frontRearCameraQuantity != 0) {
			items.add(new CarAccessory("Front & Rear Camera", 100f, frontRearCameraQuantity, user_id));
		}
		sess.setAttribute("items", items);
		
		resp.sendRedirect("/CarServiceSystem/car-links/checkoutItems.jsp");
	}
}
