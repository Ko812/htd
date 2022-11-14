package com.ncs.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ncs.model.EmployerCompany;

/**
 * Servlet implementation class EMUpdateApplicationStatus
 */
public class EMUpdateApplicationStatus extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession sess = req.getSession();
		int appID = Integer.parseInt(req.getParameter("appID"));
		String updateStatus = req.getParameter("updateStatus");
		EmployerCompany ec = (EmployerCompany)sess.getAttribute("logged-in-employer");
		System.out.println("appID "+appID + " status "+updateStatus);
		if(ec.updateApplicationStatus(appID, updateStatus)) {
			sess.setAttribute("outcome", "Application status updated!");
			sess.setAttribute("outcome-read", Boolean.parseBoolean("false"));
			resp.sendRedirect("/job-portal/employer/employerDashboard.jsp?currentView=view-job-postings");
		}
		else {
			sess.setAttribute("outcome", "Failed to update application status.");
			sess.setAttribute("outcome-read", Boolean.parseBoolean("false"));
			resp.sendRedirect("/job-portal/employer/employerDashboard.jsp?currentView=view-applications");
		}
	}

}
