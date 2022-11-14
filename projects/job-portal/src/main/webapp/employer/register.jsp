<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Land A Job - Sign up</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
<link href="/job-portal/style.css" rel="stylesheet">
</head>
<body>
	<header class="nav-bar-header">
		<div class="site-logo"><a href="/job-portal/index.jsp" class="site-logo-link">Land A Job</a></div>
		<nav class="nav-bar">
			<button onclick="back()" type="button" class="login-button">Back</button>		
		</nav>
	</header>
	<div class="container-stack">
		<div class="background-hero"></div>
		<div class="body-container">
			<form class="p-4 p-md-5 border rounded-3 bg-light" action="/job-portal/EMRegister" method="post">
				<div class="form-title">Sign up as Employer</div>
				<div class="form-floating mb-3">
					<input type="text" name="companyName" class="form-control" placeholder="Company Name" id="floatingCompanyName">
					<label for="floatingCompanyName">Company Name</label>
					<div id="nameInvalidMessage" style="display:none"><small style="color: red">Name cannot contain digits.</small></div>
				</div>
				<div class="form-floating mb-3">
					<input type="text" name="userName" class="form-control" placeholder="User name" id="floatingUserName">
					<label for="floatingUserName">User name</label>
				</div>
				<div class="form-floating mb-3">
					<input type="password" name="password" class="form-control" placeholder="Password" id="floatingPassword">
					<label for="floatingPassword">Password</label>
				</div>
				<div class="form-floating mb-3">
					<input type="text" name="companyEmail" class="form-control" placeholder="Email" id="floatingCompanyEmail">
					<label for="floatingCompanyEmail">Company Email</label>
				</div>
				<%
  					String formMessage = (String) session.getAttribute("register-employer-form-message");
					Boolean formMessageRead = (Boolean) session.getAttribute("form-message-read");
 					if(formMessage != null && !formMessageRead){
 						session.setAttribute("form-message-read", Boolean.parseBoolean("true"));
 					%>
						<div class="form-message">
							<%=formMessage%>
						</div>	
				<%}%>
				<div class="row">
					<div class="row-item">
						<input type="reset" class="login-button" value="Reset">
					</div>
					<div class="row-item">
						<button type="submit" class="login-button">Register</button>
					</div>
				</div>
			</form>
		</div>
	</div>
	<script>
		const back = () => {
			history.back();
		}
	</script>
</body>
</html>