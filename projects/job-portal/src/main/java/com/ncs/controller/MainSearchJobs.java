package com.ncs.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ncs.model.DBOps;
import com.ncs.model.EmployerCompany;
import com.ncs.model.Job;
import com.ncs.model.SearchUtility;

/**
 * Servlet implementation class SearchJobs
 */
public class MainSearchJobs extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession sess = req.getSession(true);
		String searchText = req.getParameter("searchText");
		String searchBy = req.getParameter("searchBy");
		String searchFrom = req.getParameter("searchFrom");
		SearchUtility searchUtility = new SearchUtility(searchText, searchBy);
		List<Job> results = searchUtility.search();
		sess.setAttribute("searchResults", results);
		if(searchFrom.equals("home")) {
			resp.sendRedirect("/job-portal/index.jsp");
			return;
		}
		else if (searchFrom.equals("jobSeekerDashboard")) {
			resp.sendRedirect("/job-portal/seeker/jobSeekerDashboard.jsp?currentView=search-jobs");
			return;
		}
		
	}

}
