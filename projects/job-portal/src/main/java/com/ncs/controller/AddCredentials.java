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
import com.ncs.model.JobSeeker;

/**
 * Servlet implementation class AddCredentials
 */
public class AddCredentials extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession sess = req.getSession();
		int yearsOfExp = Integer.parseInt(req.getParameter("yearsOfExp"));
		int i = 1;
		List<Acad> acads = new ArrayList<Acad>();
		while(req.getParameter("acad"+i) != null) {
			if(!req.getParameter("acad"+i).isBlank()) {
				acads.add(Acad.valueOf(req.getParameter("acad"+i)));
			}
			i++;
		}
		i = 1;
		List<String> workExps = new ArrayList<String>();
		while(req.getParameter("workExp"+i) != null) {
			if(!req.getParameter("workExp"+i).isBlank()) {
				workExps.add(req.getParameter("workExp"+i));
			}
			i++;
		}
		i = 1;
		List<String> awards = new ArrayList<String>();
		while(req.getParameter("award"+i) != null) {
			if(!req.getParameter("award"+i).isBlank()) {
				awards.add(req.getParameter("award"+i));
			}
			i++;
		}
		JobSeeker js = (JobSeeker) sess.getAttribute("logged-in-job-seeker");
		js.setYearsOfExperience(yearsOfExp);
		js.setAcadDetails(acads);
		js.setWorkExp(workExps);
		js.setAwards(awards);
		sess.setAttribute("logged-in-job-seeker", js);
		if(js.updateCredentials(acads, workExps, awards, sess)) {
			sess.setAttribute("outcome", "Credentials saved.");
			sess.setAttribute("outcome-read", Boolean.parseBoolean("false"));
		}
		else {
			sess.setAttribute("outcome", "Credentials update failed.");
			sess.setAttribute("outcome-read", Boolean.parseBoolean("false"));
		}
		String pageFlow = (String) sess.getAttribute("flow");
		String redirectUrl = "/job-portal/seeker/jobSeekerDashboard.jsp";
		if(pageFlow != null && pageFlow.equals("job-application-flow")){
			redirectUrl = "/job-portal/seeker/jobSeekerDashboard.jsp?currentView=apply-job";
		} else if (pageFlow != null && pageFlow.equals("job-application-edit-flow")){
			redirectUrl = "/job-portal/seeker/jobSeekerDashboard.jsp?currentView=edit-job-application";
		}
		resp.sendRedirect(redirectUrl);
	}
}
