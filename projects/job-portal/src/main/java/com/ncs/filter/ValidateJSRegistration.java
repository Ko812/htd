package com.ncs.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class ValidateJSRegistration
 */
@WebFilter(dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD }
, urlPatterns = { "/ValidateJSRegistration" }, servletNames = { "JSRegister" })
public class ValidateJSRegistration implements Filter {

 
    public ValidateJSRegistration() {
        // TODO Auto-generated constructor stub
    }

	public void destroy() {
		// TODO Auto-generated method stub
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession sess = req.getSession(true);
//		String firstName = req.getParameter("firstName");
//		String lastName = req.getParameter("lastName");
//		String userName = req.getParameter("userName");
//		String password = req.getParameter("password");
//		String email = req.getParameter("email");
//		String NRIC = req.getParameter("NRIC");
//		String contact = req.getParameter("contact");
//		PrintWriter out = res.getWriter();
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}
	
	public boolean validateName(String name) {
		String digits = "0123456789";
		for(int i=0;i<10;i++) {
			if(name.contains(digits.charAt(i)+"")) {
				return false;
			}
		}
		return true;
	}

}
