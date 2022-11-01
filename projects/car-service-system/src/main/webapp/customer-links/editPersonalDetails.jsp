<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Car Service System - Update Account Details</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
<link href="../style.css" rel="stylesheet">

</head>
<body>
	<div class="container-stack">
		<div class="background-hero"></div>
		<div class="body-container">
			<form class="p-4 p-md-5 border rounded-3 bg-light" action="/CarServiceSystem/UpdatePersonalDetails">
				<div class="form-title">Update Account Details</div>
				<div class="form-floating mb-3">
					<input type="text" name="name" class="form-control" placeholder="Name" id="floatingName" value="<%=(String) session.getAttribute("customer_name")%>">
					<label for="floatingName">Name</label>
				</div>
				<div class="form-floating mb-3">
					<input type="text" name="userName" class="form-control" placeholder="User name" id="floatingUserName" value="<%=(String) session.getAttribute("username")%>">
					<label for="floatingUserName">User name</label>
				</div>
				<div class="form-floating mb-3">
					<input type="text" name="email" class="form-control" placeholder="Email" id="floatingEmail" value="<%=(String) session.getAttribute("customer_email")%>">
					<label for="floatingEmail">Email</label>
				</div>
				<%
					String formMessage = (String) session.getAttribute("form-message");
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
						<input type="submit" value="Update" class="btn btn-primary">
					</div>
				</div>
			</form>
		</div>
	</div>
	<script src="form.js"></script>
	<!-- JavaScript Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
	
</body>
</html>