<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Car Service System - Registration</title>
<!-- CSS only -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
<link href="../style.css" rel="stylesheet">

</head>
<body>
	
	<div class="container-stack">
		<div class="background-hero"></div>
		<div class="body-container">
			<form class="p-4 p-md-5 border rounded-3 bg-light" action="/CarServiceSystem/Register">
				<div class="form-title">Account Registration</div>
				<div class="form-floating mb-3">
					<input type="text" name="name" class="form-control" placeholder="Name" id="floatingName">
					<label for="floatingName">Name</label>
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
					<input type="text" name="email" class="form-control" placeholder="Email" id="floatingEmail">
					<label for="floatingEmail">Email</label>
				</div>
				<div class="form-floating mb-3">
					<input type="text" name="contact" class="form-control" placeholder="Contact Number" id="floatingContact">
					<label for="floatingContact">Contact Number</label>
					<div id="contactInvalidMessage" style="display:none"><small style="color: red">Contact should have 8 digits.</small></div>
				</div>
				<div class="form-floating mb-3">
					<input type="text" name="license_ID" class="form-control" placeholder="License ID" id="floatingLicenseID">
					<label for="floatingLicenseID">License ID</label>
				</div>
				<div class="form-floating mb-3">
					<input type="date" name="license_issue_date" class="form-control" placeholder="License Issue Date" id="floatingLicenseIssueDate">
					<label for="floatingLicenseIssueDate">License Issue Date</label>
					<div id="licenseDateValidationMessage" style="display:none"><small style="color: red">License issue date cannot be later than today.</small></div>
				</div>
<%-- 				<% --%>
<!--  					String formMessage = (String) session.getAttribute("register-form-message"); -->
<%-- 					if(formMessage != null){%> --%>
<!-- 						<div class="form-message"> -->
<%-- 							<%=formMessage%> --%>
<!-- 						</div>	 -->
<%-- 				<%}%> --%>
				
				<div class="row">
					<div class="row-item">
						<button type="button" onclick="back()" class="btn btn-primary">Return</button>
					</div>
					<div class="row-item">
						<input type="submit" value="Register" class="btn btn-primary">
					</div>
				</div>
			</form>
		</div>
	</div>
	<script>
	
	function back(){
		history.back();
	}
	
	const digits = "0123456789";
	const nameInput = document.getElementById("floatingName");
	const nameValidationMessage = document.getElementById("nameInvalidMessage");
	const contact = document.getElementById("floatingContact");
	const contactValidationMessage = document.getElementById("contactInvalidMessage");
	const licenseDate = document.getElementById("floatingLicenseIssueDate");
	const licenseDateValidationMessage = document.getElementById("licenseDateValidationMessage");
	console.log("Script ran");
	nameInput.addEventListener('keyup',function(ev){
		const nameVal = ev.target.value;
		console.log("name event detected");
		for(let d of digits){
			if(nameVal.includes(d)){
				nameValidationMessage.style.display = "block";
				return;
			}	
		}
		nameValidationMessage.style.display = "none";
	});
	contact.addEventListener('keyup',function(ev){
		const conVal = ev.target.value;
		console.log("contact event detected");
		if(conVal.length != 8 ){
			contactValidationMessage.style.display = "block";
			return;
		}
		contactValidationMessage.style.display = "none";
	});

	licenseDate.addEventListener('change',function(ev){
		const dateVal = Date.parse(ev.target.value);
		console.log(dateVal);
		const today = Date.now();
		console.log(today);
		if(dateVal > today){
			licenseDateValidationMessage.style.display = "block";
			return;
		}
		licenseDateValidationMessage.style.display = "none";
	});
	</script>
	<!-- JavaScript Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
</body>
</html>