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
	<%
		String jobIDStr = request.getParameter("jobIDStr");
		if(jobIDStr == null){
			jobIDStr = (String) session.getAttribute("jobIDStr");
		}
	%>
	<div class="container-stack">
		<div class="background-hero"></div>
		<div class="body-container">
			<form class="p-4 p-md-5 border rounded-3 bg-light" action="/job-portal/JSRegister" method="post" id="js-registration-form">
				<div class="form-title">Account Registration</div>
				<div class="form-name-row">
					<div class="form-floating mb-3 form-name-group">
						<input type="text" name="firstName" class="form-control" placeholder="First Name" id="floatingFirstName" required maxlength="50">
						<label for="floatingFirstName">First Name</label>
						<div id="firstNameInvalidMessage" style="display:none"><small style="color: red">First name cannot contain digits.</small></div>
					</div>
					<div class="form-floating mb-3 form-name-group">
						<input type="text" name="lastName" class="form-control" placeholder="Last Name" id="floatingLastName" required maxlength="50">
						<label for="floatingLastName">Last Name</label>
						<div id="lastNameInvalidMessage" style="display:none"><small style="color: red">Last name cannot contain digits.</small></div>
					</div>
				</div>
				<div class="form-floating mb-3">
					<input type="text" name="userName" class="form-control" placeholder="User name" id="floatingUserName" required maxlength="50">
					<label for="floatingUserName">User name</label>
					<div id="usernameInvalidMessage" style="display:none"><small style="color: red">User name can only contain alphanumeric characters.</small></div>
				</div>
				<div class="form-floating mb-3">
					<input type="password" name="password" class="form-control" placeholder="Password" id="floatingPassword" required maxlength="24">
					<label for="floatingPassword">Password</label>
					<i class="bi bi-eye-slash" id="togglePasswordVis" style="font-size: 20px; position: absolute; top: 12px; right: 15px; cursor: pointer;"></i>
					<i class="bi bi-eye" id="togglePasswordInvis" style="font-size: 20px; position: absolute; top: 12px; right: 15px; cursor: pointer; display:none;"></i>
					<small id="password-req-message" style="display:none; color:red;">Must be at least 8 characters long, contain <br>at least 1 numeric digit, 1 capital letter, 1 small <br>letter and 1 special character.</small>
				</div>
				
				<div class="form-floating mb-3">
					<input type="text" name="email" class="form-control" placeholder="Email" id="floatingEmail" required maxlength="50">
					<label for="floatingEmail">Email</label>
					<small id="emailValidationMessage" style="display:none; color:red;">Email address not in a valid format.</small>
				</div>
				<div class="form-floating mb-3">
					<input type="text" name="NRIC" class="form-control" placeholder="Identification Number" id="floatingNRIC" required>
					<label for="floatingNRIC">Identification Number</label>
					<div id="nricInvalidMessage" style="display:none"><small style="color: red">NRIC format is invalid.</small></div>
					<div id="nricInvalidMessage2" style="display:none"><small style="color: red">NRIC is invalid.</small></div>
				</div>
				<div class="form-floating mb-3">
					<input type="text" name="contact" class="form-control" placeholder="Contact Number" id="floatingContact" pattern="[0-9]{8}" required maxlength="8">
					<label for="floatingContact">Contact Number</label>
					<div id="contactInvalidMessage" style="display:none"><small style="color: red">Contact should have 8 digits.</small></div>
				</div>
				<%
  					String formMessage = (String) session.getAttribute("register-job-seeker-form-message");
					Boolean formMessageRead = (Boolean) session.getAttribute("form-message-read");
 					if(formMessage != null && !formMessageRead){
 						session.setAttribute("form-message-read", Boolean.parseBoolean("true"));
 				%>
						<div class="form-message">
							<%=formMessage%>
						</div>	
				<%}%>
				<input type="text" name="jobIDStr" value="<%=jobIDStr%>" style="display:none">
				<div class="row">
					<div class="row-item">
						<input type="reset" value="Clear" class="login-button">
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
	function back(){
		history.back();
	}

	const firstNameInput = document.getElementById("floatingFirstName");
	const lastNameInput = document.getElementById("floatingLastName");
	const firstNameValidationMessage = document.getElementById("firstNameInvalidMessage");
	const lastNameValidationMessage = document.getElementById("lastNameInvalidMessage");
	const usernameEl = document.getElementById("floatingUserName");
	const usernameValidationMessage = document.getElementById("usernameInvalidMessage");
	const pwdEl = document.getElementById("floatingPassword");
	const emailEl = document.getElementById("floatingEmail");
	const invalidEmailMessage = document.getElementById("emailValidationMessage");
	const invalidPasswordMessage = document.getElementById("password-req-message");
	const contact = document.getElementById("floatingContact");
	const contactValidationMessage = document.getElementById("contactInvalidMessage");
	const nricInput = document.getElementById("floatingNRIC");
	const nricValidationMessage = document.getElementById("nricInvalidMessage");
	const nricValidationMessage2 = document.getElementById("nricInvalidMessage2");
	
	firstNameInput.addEventListener('keyup',function(ev){
		const nameVal = ev.target.value;
		
		if(!nameValidator(nameVal)){
			console.log("invalid name" + nameVal);	
			firstNameValidationMessage.style.display = "block";
			return;
		}
		firstNameValidationMessage.style.display = "none";
	});
	lastNameInput.addEventListener('keyup',function(ev){
		const nameVal = ev.target.value;
		if(!nameValidator(nameVal)){
			lastNameValidationMessage.style.display = "block";
			return;
		}
		lastNameValidationMessage.style.display = "none";
	});
	
	usernameEl.addEventListener('keyup',function(ev){
		const nameVal = ev.target.value;
		if(!isAngloTextWithDigits(nameVal)){
			usernameValidationMessage.style.display = "block";
			return;
		}
		usernameValidationMessage.style.display = "none";
	});
	
	pwdEl.addEventListener("keyup", (event) => {
		if(!validPasswordWithSpecialChar(event.target.value)){
			invalidPasswordMessage.style.display = "block";
			return;
		}
		invalidPasswordMessage.style.display = "none";
	});
	
	emailEl.addEventListener("keyup", (event) => {
		if(!validEmail(event.target.value)){
			invalidEmailMessage.style.display = "block";
			return;
		}
		invalidEmailMessage.style.display = "none";
	});
	
	contact.addEventListener('keyup',function(ev){
		const conVal = ev.target.value;
		if(!contactValidator(conVal)){
			contactValidationMessage.style.display = "block";
			return;
		}
		contactValidationMessage.style.display = "none";
	});

	nricInput.addEventListener('keyup',function(ev){
		let nricVal = ev.target.value.toUpperCase();
		if(!NRICValidator(nricVal)){
			nricValidationMessage.style.display = "block";
			return;
		}
		nricValidationMessage.style.display = "none";
		nricValidationMessage2.style.display = "none";
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
	ensureValidation("js-registration-form", 
			[
				nameValidator,
				nameValidator,
				isAngloTextWithDigits,
				validPasswordWithSpecialChar,
				validEmail,
				contactValidator,
				NRICValidator,
				],
			[
				"floatingFirstName",
				"floatingLastName",
				"floatingUserName",
				"floatingPassword",
				"floatingEmail",
				"floatingContact",
				"floatingNRIC",
			]
	);
	</script>
</body>
</html>