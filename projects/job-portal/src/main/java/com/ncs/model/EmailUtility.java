package com.ncs.model;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailUtility {
	
	public static void emailWithoutAuthentication() {
		
	    System.out.println("SimpleEmail Start");
	    
	    Properties prop = new Properties();
		
	    prop.put("mail.smtp.host", "smtp.gmail.com");
	    prop.put("mail.smtp.port", 587);
	    prop.put("mail.smtp.auth", "true");
	    prop.put("mail.smtp.starttls.enable", "true");
		
	    String smtpHostServer = "smtp.gmail.com";
	    String emailID = "ncshtd29@gmail.com";

	    Session session = Session.getInstance(prop, null);
	    
	    EmailUtility.sendEmail(session, emailID,"SimpleEmail Testing Subject", "SimpleEmail Testing Body");
	}
	
	public static boolean emailWithPassword(String to, String sc, String loginAs) {
		String from = "appDeveloper@initkopico.com";
		String host = "localhost";
		Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", 587);
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true");
		Session mailSession = Session.getDefaultInstance(prop, new javax.mail.Authenticator(){
			protected PasswordAuthentication getPasswordAuthentication(){
				PasswordAuthentication pa = new PasswordAuthentication("appDeveloper@initkopico.com", "oddagvstspjhpmrk");
				return pa;}});
		try {
			MimeMessage message = new MimeMessage(mailSession);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject("Land A Job - Reset Password");
			message.addHeader("Content-type", "text/HTML; charset=UTF-8");
			message.addHeader("format", "flowed");
			message.addHeader("Content-Transfer-Encoding", "8bit");
			
			MimeBodyPart bodyPart = new MimeBodyPart();
			bodyPart.setContent("<html><body>" + 
					"Dear user, " 
					+ "<br>" + "<br>" + "<br>" + "<br>"
					+ "You have requested to reset password. Click on the link <br><br><a href=\"http://localhost:8080/job-portal/resetPassword.jsp?email="+to+"&sessionCode=" + sc+ "&loginAs="+loginAs+"\">http://localhost:8080/job-portal/resetPassword.jsp</a><br><br> to reset your password. The link is valid for 5 minutes only."
					+ "<br>" + "<br>" + "<br>" + "<br> " 
					+ "LandAJob Admin" + "</body></html>", "text/html");
			Multipart mp = new MimeMultipart();
			mp.addBodyPart(bodyPart);
 			message.setContent(mp);
			Transport.send(message);
			System.out.println("Email sent successfully....");
			return true;
	   } catch (Exception mex) {
	      mex.printStackTrace();
	      System.out.println("Error: unable to send email...." + mex.getMessage());
	      return false;
	   }
	}
	
	static void sendEmail(Session session, String toEmail, String subject, String body){
		try
	    {
	      MimeMessage msg = new MimeMessage(session);
	      //set message headers
	      msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
	      msg.addHeader("format", "flowed");
	      msg.addHeader("Content-Transfer-Encoding", "8bit");

	      msg.setFrom(new InternetAddress("no_reply@example.com", "NoReply-JD"));
	      msg.setReplyTo(InternetAddress.parse("no_reply@example.com", false));
	      msg.setSubject(subject, "UTF-8");
	      msg.setText(body, "UTF-8");
	      msg.setSentDate(new Date());

	      msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
	      System.out.println("Message is ready");
    	  Transport.send(msg);  

	      System.out.println("EMail Sent Successfully!!");
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	    }
	}
	
	public static boolean validateEmail(String email) {
		boolean containAdSymbol = email.contains("@");
		return containAdSymbol;
	}
	

	
	public void saveDraft(MimeMessage message) {
//		Folder draftsMailBoxFolder = imapsStore.getFolder("inbox");//[Gmail]/Drafts
//	    draftsMailBoxFolder.open(Folder.READ_WRITE);    
//	    draftMessage.setFlag(Flag.DRAFT, true);
//	    MimeMessage draftMessages[] = {draftMessage};
//	    draftsMailBoxFolder.appendMessages(draftMessages);
	}
}
