package com.ncs.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ncs.model.JobSeeker;

/**
 * Servlet implementation class ManageJobSeekerDetails
 */
public class ManageJobSeekerDetails extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession sess = req.getSession();
		JobSeeker js = (JobSeeker) sess.getAttribute("logged-in-job-seeker");
		js.setFirstName(req.getParameter("firstName"));
		js.setLastName(req.getParameter("lastName"));
		String userNameParameter = req.getParameter("userName");
		js.setEmail(req.getParameter("email"));
		js.setNRIC(req.getParameter("NRIC"));
		js.setContact(req.getParameter("contact"));
		if(userNameParameter.equals(js.getUserName())) {
			if(js.updateDetails(sess)) {
				sess.setAttribute("logged-in-job-seeker", js);
				sess.setAttribute("outcome", "Details update successfully.");
				sess.setAttribute("outcome-read", Boolean.parseBoolean("false"));
			}
			else {
				sess.setAttribute("outcome", "Details update failed.");
				sess.setAttribute("outcome-read", Boolean.parseBoolean("false"));
			}
		}
		else {
			if(js.updateDetails(sess, userNameParameter)) {
				js.setUserName(userNameParameter);
				sess.setAttribute("logged-in-job-seeker", js);
				sess.setAttribute("outcome", "Details update successfully.");
				sess.setAttribute("outcome-read", Boolean.parseBoolean("false"));
			}
			else {
				sess.setAttribute("outcome", "Details update failed.");
				sess.setAttribute("outcome-read", Boolean.parseBoolean("false"));
			}
		}
		
		resp.sendRedirect("/job-portal/seeker/jobSeekerDashboard.jsp");
	}

}
