<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Land A Job - Register</title>
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
			<form class="p-4 p-md-5 border rounded-3 bg-light" action="/job-portal/JSRegister" method="post">
				<div class="form-title">Account Registration</div>
				<div class="form-name-row">
					<div class="form-floating mb-3 form-name-group">
						<input type="text" name="firstName" class="form-control" placeholder="First Name" id="floatingFirstName">
						<label for="floatingFirstName">First Name</label>
						<div id="firstNameInvalidMessage" style="display:none"><small style="color: red">First name cannot contain digits.</small></div>
					</div>
					<div class="form-floating mb-3 form-name-group">
						<input type="text" name="lastName" class="form-control" placeholder="Last Name" id="floatingLastName">
						<label for="floatingLastName">Last Name</label>
						<div id="lastNameInvalidMessage" style="display:none"><small style="color: red">Last name cannot contain digits.</small></div>
					</div>
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
					<input type="text" name="email" class="form-control" placeholder="Email" id="floatingEmail">
					<label for="floatingEmail">Email</label>
				</div>
				<div class="form-floating mb-3">
					<input type="text" name="NRIC" class="form-control" placeholder="Identification Number" id="floatingNRIC">
					<label for="floatingNRIC">Identification Number</label>
					<div id="nricInvalidMessage" style="display:none"><small style="color: red">NRIC format is invalid.</small></div>
					<div id="nricInvalidMessage2" style="display:none"><small style="color: red">NRIC is invalid.</small></div>
				</div>
				<div class="form-floating mb-3">
					<input type="text" name="contact" class="form-control" placeholder="Contact Number" id="floatingContact" minlength="8" maxlength="8">
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
	<script>
	
	function back(){
		history.back();
	}
	const alphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	const validNRICInitialChars = "STFGM";
	const digits = "0123456789";
	const firstNameInput = document.getElementById("floatingFirstName");
	const lastNameInput = document.getElementById("floatingLastName");
	const firstNameValidationMessage = document.getElementById("firstNameInvalidMessage");
	const lastNameValidationMessage = document.getElementById("lastNameInvalidMessage");
	const contact = document.getElementById("floatingContact");
	const contactValidationMessage = document.getElementById("contactInvalidMessage");
	const nricInput = document.getElementById("floatingNRIC");
	const nricValidationMessage = document.getElementById("nricInvalidMessage");
	const nricValidationMessage2 = document.getElementById("nricInvalidMessage2");
	const nricChecksum = (numStr, init) => {
		const sum = 2 * Number(numStr[0]) + 7 * Number(numStr[1]) + 6 * Number(numStr[2]) + 5 * Number(numStr[3]) + 4 * Number(numStr[4]) + 3 * Number(numStr[5]) + 2 * Number(numStr[6]);
		if(init == "T" || init == "G"){
			sum = sum + 4;
		}
		const r = sum % 11;
		if(init == "S" || init == "T"){
			switch(r){
				case 10:
					return "A";
				case 9:
					return "B";
				case 8:
					return "C";
				case 7:
					return "D";
				case 6:
					return "E";
				case 5:
					return "F";
				case 4:
					return "G";
				case 3:
					return "H";
				case 2:
					return "I";
				case 1:
					return "Z";
				case 0:
					return "J";
				default:
					return "-";
			}
		}
		else if (init == "F" || init == "G"){
			switch(r){
				case 10:
					return "K";
				case 9:
					return "L";
				case 8:
					return "M";
				case 7:
					return "N";
				case 6:
					return "P";
				case 5:
					return "Q";
				case 4:
					return "R";
				case 3:
					return "T";
				case 2:
					return "U";
				case 1:
					return "W";
				case 0:
					return "X";
				default:
					return "-";
			}
		}
		return "-";
	}
	firstNameInput.addEventListener('keyup',function(ev){
		const nameVal = ev.target.value;
		for(let d of digits){
			if(nameVal.includes(d)){
				firstNameValidationMessage.style.display = "block";
				return;
			}	
		}
		firstNameValidationMessage.style.display = "none";
	});
	lastNameInput.addEventListener('keyup',function(ev){
		const nameVal = ev.target.value;
		for(let d of digits){
			if(nameVal.includes(d)){
				lastNameValidationMessage.style.display = "block";
				return;
			}	
		}
		lastNameValidationMessage.style.display = "none";
	});
	
	contact.addEventListener('keyup',function(ev){
		const conVal = ev.target.value;
		if(conVal.length != 8 ){
			contactValidationMessage.style.display = "block";
			return;
		}
		contactValidationMessage.style.display = "none";
	});

	nricInput.addEventListener('keyup',function(ev){
		let nricVal = ev.target.value.toUpperCase();
		const firstChar = nricVal[0];
		const lastChar = nricVal[nricVal.length - 1];
		console.log("validating nric" + nricVal);
		if(!validNRICInitialChars.includes(firstChar)){
			nricValidationMessage.style.display = "block";
			return;
		}
		if(nricVal.length != 9){
			nricValidationMessage.style.display = "block";
			return;
		}
		if(!alphabets.includes(lastChar)){
			nricValidationMessage.style.display = "block";
			return;
		}
		const numericPart = nricVal.substring(1,8);
		if(nricChecksum(numericPart, firstChar) != lastChar){
			nricValidationMessage2.style.display = "block";
			return;
		}
		nricValidationMessage.style.display = "none";
		nricValidationMessage2.style.display = "none";
	});
	
	
	</script>
</body>
</html>