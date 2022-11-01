<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@ page import = "java.lang.*"%>
<%@ page import = "java.io.*,java.util.*,javax.mail.*"%>
<%@ page import = "javax.mail.internet.*,javax.activation.*"%>
<%@ page import = "javax.servlet.http.*,javax.servlet.*" %>

<%
   String result;
   
   // Recipient's email ID needs to be mentioned.
   String to = (String) session.getAttribute("userEmail");

   // Sender's email ID needs to be mentioned
   String from = "avensysncs@gmail.com";

   // Assuming you are sending email from localhost
   String host = "localhost";

   // Get system properties object

   Properties prop = new Properties();
	
   prop.put("mail.smtp.host", "smtp.gmail.com");
   prop.put("mail.smtp.port", 587);
   prop.put("mail.smtp.auth", "true");
   prop.put("mail.smtp.starttls.enable", "true");

   // Get the default Session object.
   Session mailSession = Session.getDefaultInstance(prop, new javax.mail.Authenticator()
		{
			protected PasswordAuthentication getPasswordAuthentication()
			{
				//sender's mail id and pwd is encapsulated inside "SendersCredentials.java"
				return new PasswordAuthentication(from, "*avensysNCS*");
			}
		});

   try {
      // Create a default MimeMessage object.
      MimeMessage message = new MimeMessage(mailSession);
      
      // Set From: header field of the header.
      message.setFrom(new InternetAddress(from));
      
      // Set To: header field of the header.
      message.addRecipient(Message.RecipientType.TO,
                               new InternetAddress(to));
      // Set Subject: header field
      message.setSubject("Reset Password for Car Service System");
      
      // Now set the actual message
      message.setText("Dear user, please click the link 'localhost:8089/CarServiceSystem/customer-links/resetPasswordForUser.jsp");
      
      // Send message
      Transport.send(message);
      result = "Sent message successfully....";
   } catch (MessagingException mex) {
      mex.printStackTrace();
      result = "Error: unable to send message....";
   }
%>

<html>
   <head>
      <title>Car Service System - Send Reset Password Link</title>
   </head>
   
   <body>
      <h1>Send Email using JSP</h1>
      
      <p align = "center">
         <% 
            out.println("Result: " + result + "\n");
         %>
      </p>
   </body>
</html>