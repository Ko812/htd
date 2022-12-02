<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Land A Job - Register</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
<link rel="stylesheet" href=
"https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css" />
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
			<form class="p-4 p-md-5 border rounded-3 bg-light" action="/job-portal/EMRegister" method="post" id="employer-registration-form">
				<div class="form-title">Sign up as Employer</div>
				<div class="form-floating mb-3">
					<input type="text" name="companyName" class="form-control" placeholder="Company Name" id="floatingCompanyName" required maxlength="50">
					<label for="floatingCompanyName">Company Name</label>
				</div>
				<div class="form-floating mb-3">
					<input type="text" name="userName" class="form-control" placeholder="User name" id="floatingUserName"  required maxlength="50">
					<label for="floatingUserName">User name</label>
				</div>
				<div class="form-floating mb-3">
					<input type="password" name="password" class="form-control" placeholder="Password" id="floatingPassword" maxlength="24" required>
					<label for="floatingPassword">Password</label>
					<i class="bi bi-eye-slash" id="togglePasswordVis" style="font-size: 20px; position: absolute; top: 12px; right: 15px; cursor: pointer;"></i>
					<i class="bi bi-eye" id="togglePasswordInvis" style="font-size: 20px; position: absolute; top: 12px; right: 15px; cursor: pointer; display:none;"></i>
					<small id="password-req-message" style="display:none; color:red;">Must be at least 8 characters long, contain <br>at least 1 numeric digit, 1 capital letter, 1 small <br>letter and 1 special character.</small>
				</div>
				
				<div class="form-floating mb-3">
					<input type="text" name="companyEmail" class="form-control" placeholder="Email" id="floatingCompanyEmail" required maxlength="50">
					<label for="floatingCompanyEmail">Company Email</label>
					<small id="emailValidationMessage" style="display:none; color:red;">Email address not in a valid format.</small>
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
	<script src="../validation.js" type="text/javascript"></script>
	<script>
		const back = () => {
			history.back();
		}
		const pwdEl = document.getElementById("floatingPassword");
		const invalidPasswordMessage = document.getElementById("password-req-message");
		pwdEl.addEventListener("keyup", (event) => {
			if(!validPasswordWithSpecialChar(event.target.value)){
				invalidPasswordMessage.style.display = "block";
				return;
			}
			invalidPasswordMessage.style.display = "none";
		});
		
		const emailEl = document.getElementById("floatingCompanyEmail");
		const invalidEmailMessage = document.getElementById("emailValidationMessage");
		emailEl.addEventListener("keyup", (event) => {
			if(!validEmail(event.target.value)){
				invalidEmailMessage.style.display = "block";
				return;
			}
			invalidEmailMessage.style.display = "none";
		});
		
		const togglePasswordVis = document
	    .querySelector('#togglePasswordVis');
		const togglePasswordInvis = document
	    .querySelector('#togglePasswordInvis');

		togglePasswordVis.addEventListener('click', () => {
		
		    // Toggle the type attribute using
		    // getAttribure() method
		          
		    pwdEl.setAttribute('type', 'text');
		
		    // Toggle the eye and bi-eye icon
		    togglePasswordVis.style.display = "none";
		    togglePasswordInvis.style.display = "inline";
		});
		
		togglePasswordInvis.addEventListener('click', () => {
			
		    // Toggle the type attribute using
		    // getAttribure() method
		          
		    pwdEl.setAttribute('type', 'password');
		
		    // Toggle the eye and bi-eye icon
		    togglePasswordVis.style.display = "inline";
		    togglePasswordInvis.style.display = "none";
		});
		
		ensureValidation(
			"employer-registration-form", 
			[
				isAngloText,
				isAngloText,
				validPasswordWithSpecialChar,
				validEmail,	
			],
			[
				"floatingCompanyName",
				"floatingUserName",
				"floatingPassword",
				"floatingCompanyEmail"
			]
		);
	</script>
</body>
</html>