<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Car Service System - Forget Password</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
<link href="../style.css" rel="stylesheet">

</head>
<body>
	<div class="container-stack">
		<div class="background-hero"></div>
		<div class="body-container">
		<form class="p-4 p-md-5 border rounded-3 bg-light" action="/CarServiceSystem/SendResetPasswordEmail">
		<div class="form-title">Send Forget Password Email</div>
		<div class="form-floating mb-3">
			<input type="text" name="userEmail" class="form-control" placeholder="User email" id="floatingEmail">
			<label for="floatingEmail">User email</label>
		</div>
		<%
			String formMessage = (String) session.getAttribute("send-reset-password-form-message");
			if(formMessage != null){%>
			<div class="form-message">
				<%=formMessage%>
			</div>	
		<%}%>
		<div class="row">
			<div class="row-item">
				<button type="button" onclick="back()" class="btn btn-primary">Return</button>
			</div>
			<div class="row-item">
				<input type="submit" value="Reset Password" class="btn btn-primary">
			</div>
		</div>
		</form>
		</div>
	</div>
	<script src="form.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
	
</body>
</html>