<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Car Service System - Welcome</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
<link href="../style.css" rel="stylesheet">
</head>
<body>
	<script>
		function deleteAcc(){
			let cfm = confirm("Confirm delete account?");
			if(cfm){
				location.href = '/CarServiceSystem/DeleteAccount'	
			}
		}
		function logoutAcc(){
			let cfm = confirm("Log out now?");
			if(cfm){
				location.href = "/CarServiceSystem/Logout";	
			}
		}
	</script>
	
	<div class="container-stack">
		<div class="background-hero"></div>
	<div class="body-container">
		<h1>Welcome to the car service system</h1>
		<%
		String outcome = (String) session.getAttribute("outcome");
		if(outcome != null){
			out.println(outcome);
		}
		%>
		<br><br>
		<div class="box-title">Car Management</div>
		<div class="car-button-box">
			<button class="menu-button" type="button" onclick="location.href = '/CarServiceSystem/car-links/addCarDetails.jsp'">Add car</button>
			<button class="menu-button" type="button" onclick="location.href = '/CarServiceSystem/ViewCarDetails'">View cars</button>
			<button class="menu-button" type="button" onclick="location.href = '/CarServiceSystem/InitiateRequestService'">Request service</button>
			<button class="menu-button" type="button" onclick="location.href = '/CarServiceSystem/CheckServiceStatus'">Service status</button>
			<button class="menu-button" type="button" onclick="location.href = '/CarServiceSystem/car-links/buyAccessories.jsp'">Buy accessories</button>
		</div>
		<div class="box-title">Account Management</div>
		<div class="customer-button-box">
			<button class="menu-button" type="button" onclick="location.href = '/CarServiceSystem/customer-links/resetPassword.jsp'">Reset password</button>
			<button class="menu-button" type="button" onclick="location.href = '/CarServiceSystem/EditPersonalDetails'">Edit profile</button>
			<button class="menu-button" type="button" onclick="deleteAcc()">Delete account</button>
			<button class="menu-button" type="button" onclick="logoutAcc()">Logout</button>
		</div>
		<br>
	</div>
	</div>

	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
</body>
</html>