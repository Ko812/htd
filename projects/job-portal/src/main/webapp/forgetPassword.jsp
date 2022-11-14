<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Land A Job - Forget Password</title>
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
		<div class="body-base">
			<div class="background-hero"></div>
		</div>
		<div class="body-container">
		<form class="p-4 p-md-5 border rounded-3 bg-light" action="/job-portal/SendResetPasswordEmail">
		<div class="form-title">Send Reset Password Email</div>
		<div class="form-group">
			<input type="radio" name="loginAs" placeholder="Employer" id="employerRadio" value="employer" checked> 
			<label for="employerRadio" class="form-label">Employer</label>
			<input type="radio" name="loginAs" placeholder="Job Seeker" id="jobSeekerRadio" value="jobSeeker">
			<label for="jobSeekerRadio" class="form-label">Job Seeker</label>
		</div>
		<div class="form-floating mb-3">
			<input type="text" name="email" class="form-control" placeholder="Email" id="floatingEmail">
			<label for="floatingEmail">Email Address</label>
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
				<input type="submit" value="Send" class="login-button">
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
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
</body>
</html>