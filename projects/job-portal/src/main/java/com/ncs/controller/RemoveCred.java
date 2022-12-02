package com.ncs.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ncs.model.Acad;
import com.ncs.model.JobApplication;
import com.ncs.model.JobSeeker;

/**
 * Servlet implementation class RemoveCred
 */
public class RemoveCred extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession sess = req.getSession();
		JobSeeker js = (JobSeeker) sess.getAttribute("logged-in-job-seeker");
		String credType = req.getParameter("cred");
		int ind = Integer.parseInt(req.getParameter("ind")) - 1;
		if(credType.equals("acad")) {
			js.removeAcad(ind);
		}
		else if (credType.equals("work")) {
			js.removeWork(ind);
		}
		else {
			js.removeAward(ind);
		}
		sess.setAttribute("logged-in-job-seeker", js);
		resp.sendRedirect("/job-portal/seeker/jobSeekerDashboard.jsp?currentView=add-credentials");
	}
}
