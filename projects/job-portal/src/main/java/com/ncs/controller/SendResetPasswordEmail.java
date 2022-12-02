package com.ncs.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ncs.model.DBOps;
import com.ncs.model.EmailUtility;
import com.ncs.model.Encryption;



/**
 * Servlet implementation class SendResetPasswordEmail
 */
public class SendResetPasswordEmail extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String loginAs = req.getParameter("loginAs");
		String email = req.getParameter("email");
		HttpSession sess = req.getSession(true);
		resp.setContentType("text/html");
		DBOps db = new DBOps();
		if(!(db.verifyEmailExists(email, loginAs, sess))) {
			sess.setAttribute("send-reset-password-form-message", "Account not found.");
			sess.setAttribute("form-message-read", Boolean.parseBoolean("false"));
		}
		else {
			String sc = Encryption.generateSessionCode();
			if(db.saveSessionCode(sc, email) && EmailUtility.emailWithPassword(email, sc, loginAs)) {
				sess.setAttribute("sessionCode", sc);
				sess.setAttribute("login-form-message", "Reset password email sent!");
				sess.setAttribute("form-message-read", Boolean.parseBoolean("false"));
				resp.sendRedirect("/job-portal/login.jsp");
				return;
			}
			else {
				sess.setAttribute("send-reset-password-form-message", "Reset password email sending unsuccessful.");
				sess.setAttribute("form-message-read", Boolean.parseBoolean("false"));
			}
		}
		resp.sendRedirect("/job-portal/forgetPassword.jsp");
	}

}
