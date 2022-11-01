<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Car Service System - Customer Menu</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
<link href="style.css" rel="stylesheet">

</head>
<body>
	<div class="container-stack">
		<div class="background-hero"></div>
		<div class="body-container">
		<form class="p-4 p-md-5 border rounded-3 bg-light" action="/CarServiceSystem/Register">
			<div class="form-title">Welcome</div>
			<div class="row">
				<div class="row-item">
					<button type="button" onclick="login()" class="btn btn-primary">Login</button>
				</div>
				<div class="row-item">
					<button type="button" onclick="register()" class="btn btn-primary">Sign up</button>
				</div>
			</div>
		</form>
	</div>
	</div>	
	<script>
		const register = () => {
			location.href = '/CarServiceSystem/customer-links/registerCustomer.jsp'
		}
		
		const login = () => {
			location.href = '/CarServiceSystem/customer-links/customerLogin.jsp'
		}
	</script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
</body>
</html>