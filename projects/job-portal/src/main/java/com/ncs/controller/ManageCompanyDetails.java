package com.ncs.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ncs.model.CompanySize;
import com.ncs.model.EmployerCompany;

/**
 * Servlet implementation class ManageCompanyDetails
 */
public class ManageCompanyDetails extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession sess = req.getSession();
		EmployerCompany ec = (EmployerCompany) sess.getAttribute("logged-in-employer");
		String companyName = req.getParameter("companyName");
		String userName = req.getParameter("userName");
		String companyEmail = req.getParameter("companyEmail");
		String companyDetails = req.getParameter("companyDetails");
		int yearsOfOperation = Integer.parseInt(req.getParameter("yearsOfOperation"));
		String vmv = req.getParameter("companyVMV");
		String companyWebsite = req.getParameter("companyWebsite");
		String companySizeString = req.getParameter("companySize");
		CompanySize companySize = CompanySize.valueOf(companySizeString.split(" ")[0]);
		
		ec.setCompanyName(companyName);
		ec.setUserName(userName);
		ec.setCompanyEmail(companyEmail);
		ec.setCompanyDetails(companyDetails);
		ec.setYearsOfOperation(yearsOfOperation);
		ec.setVmv(vmv);
		ec.setCompanyWebsite(companyWebsite);
		ec.setCompanySize(companySize);
		int outcome = ec.updateEmployer();
		if(outcome == 1) {
			sess.setAttribute("outcome", "Company account update success.");
			sess.setAttribute("outcome-read", Boolean.parseBoolean("false"));
			sess.setAttribute("logged-in-employer", ec);
			resp.sendRedirect("/job-portal/employer/employerDashboard.jsp");
		}
		else {
			sess.setAttribute("outcome", "Company account update unsuccessful.");
			sess.setAttribute("outcome-read", Boolean.parseBoolean("false"));
			resp.sendRedirect("/job-portal/employer/employerDashboard.jsp?currentView=manage-account");
		}
		
	}
}
