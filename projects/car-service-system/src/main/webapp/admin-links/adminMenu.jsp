<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Car Service System - Admin System</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
<link href="../style.css" rel="stylesheet">
</head>
<body>
	<div class="container-stack">
		<div class="background-hero"></div>
	<div class="body-container">
		<h1>Car Service Admin System</h1>
		<%
		String outcome = (String) session.getAttribute("outcome");
		if(outcome != null){
			out.println(outcome);
		}
		%>
		<br><br>
		<div class="box-title">Admin Management</div>
		<div class="admin-menu-button-box">
			<button type="button" class="menu-button" onclick="location.href = '/CarServiceSystem/ViewAllCustomers'">View all customers</button>
			<button type="button" class="menu-button" onclick="location.href = '/CarServiceSystem/ViewPendingRequests'">Pending service requests</button>
			<button type="button" class="menu-button" onclick="location.href = '/CarServiceSystem/index.jsp'">Logout</button>
		</div>
		<br>
	</div>
	</div>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
	
</body>
</html>