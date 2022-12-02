package com.ncs.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ncs.model.EmployerCompany;
import com.ncs.model.Job;
import com.ncs.model.JobSeeker;

/**
 * Servlet implementation class EMJSLogin
 */
public class EMJSLogin extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession sess = req.getSession();
		String loginAs = req.getParameter("loginAs");
		String userName = req.getParameter("userName");
		String password = req.getParameter("password");
		String jobIDStr = req.getParameter("jobIDStr");
		if(jobIDStr == null) {
			jobIDStr = (String) sess.getAttribute("jobIDStr");
		}
		sess = req.getSession(true);
		if(loginAs.equals("employer")) {
			EmployerCompany ec = EmployerCompany.login(userName, password, sess);
			if(ec != null) {
				List<Job> jobs = ec.viewJobPostings();
				sess.setAttribute("jobs", jobs);
				sess.setAttribute("logged-in-employer", ec);
				resp.sendRedirect("/job-portal/ViewJobPostings");
			}
			else {
				resp.sendRedirect("/job-portal/login.jsp");
			}
		}
		else if (loginAs.equals("jobSeeker")) {
			JobSeeker js = JobSeeker.login(userName, password, sess);
			if(js != null) {
				sess.setAttribute("applications", js.pullJobsApplied());
				sess.setAttribute("logged-in-job-seeker", js);
				if(jobIDStr != null && !jobIDStr.equals("null")) {
					Job job = js.loadJob(Integer.parseInt(jobIDStr));
					sess.setAttribute("job-to-apply", job);
					sess.setAttribute("flow", "job-application-flow");
					resp.sendRedirect("/job-portal/seeker/jobSeekerDashboard.jsp?currentView=apply-job");
					return;
				}
				resp.sendRedirect("/job-portal/ViewJobsApplied");
			}
			else {
				resp.sendRedirect("/job-portal/login.jsp");
			}
		}
	}

}
