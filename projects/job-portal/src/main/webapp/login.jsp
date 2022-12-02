<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Land A Job - Employer Login</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
<link rel="stylesheet" href=
"https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css" />
<link href="/job-portal/style.css" rel="stylesheet" type="text/css">
</head>
<body>
	<%
		String user = (String) request.getParameter("user");
		boolean checkEmployer, checkJobSeeker;
		if(user != null){
			checkEmployer = user.equals("employer");
			checkJobSeeker = !checkEmployer;
		}
		else {
			checkEmployer = true;
			checkJobSeeker = false;
		}
		String jobIDStr = request.getParameter("jobIDStr");
		if(jobIDStr == null){
			jobIDStr = (String) session.getAttribute("jobIDStr");
		}
		else{
			session.setAttribute("jobIDStr", jobIDStr);
		}
	%>
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
		<form class="p-4 p-md-5 border rounded-3 bg-light" action="/job-portal/EMJSLogin?jobIDStr=<%=jobIDStr%>" method="post">
		<div class="form-title">Login</div>
		<div class="form-group">
			<input type="radio" name="loginAs" placeholder="Employer" id="employerRadio" value="employer" <%=checkEmployer ? "checked" : ""%>> 
			<label for="employerRadio" class="form-label">Employer</label>
			<input type="radio" name="loginAs" placeholder="Job Seeker" id="jobSeekerRadio" value="jobSeeker" <%=checkJobSeeker ? "checked" : ""%>>
			<label for="jobSeekerRadio" class="form-label">Job Seeker</label>
		</div>
		<div class="form-floating mb-3">
			<input type="text" name="userName" class="form-control" placeholder="User name" id="floatingUserName" required>
			<label for="floatingUserName">User name</label>
		</div>
		<div class="form-floating mb-3">
			<input type="password" name="password" class="form-control" placeholder="Password" id="floatingPassword" required>
			<label for="floatingPassword">Password</label>
			<i class="bi bi-eye-slash" id="togglePasswordVis" style="font-size: 20px; position: absolute; top: 12px; right: 15px; cursor: pointer;"></i>
			<i class="bi bi-eye" id="togglePasswordInvis" style="font-size: 20px; position: absolute; top: 12px; right: 15px; cursor: pointer; display:none;"></i>
		</div>
		
		<a class="forgetPasswordLink" href="/job-portal/forgetPassword.jsp">Forget Password</a>
		<%
			String formMessage = (String) session.getAttribute("login-form-message");
			Boolean formMessageRead = (Boolean) session.getAttribute("form-message-read");
			if(formMessage != null && !formMessageRead){
				session.setAttribute("form-message-read", Boolean.parseBoolean("true"));
			%>
			<div class="form-message">
				<%=formMessage%>
			</div>	
		<%}%>
		<div id="errorMessage" style="display:none"><small style="color: red">Select either Employer or Job Seeker</small></div>
		<div class="row">
			<div class="row-item">
				<button type="submit" class="login-button">Login</button>
			</div>
			<div class="row-item">
				<button type="button" onclick="signUp()" class="login-button">Sign up</button>
			</div>
		</div>
			<input style="display:none" name="jobIDStr" value=<%=jobIDStr%>>
		</form>

		</div>
	</div>
	
	<script>
		const back = () => {
			history.back();
		}
		const signUp = () => {
			const employerRadioTag = document.getElementById("employerRadio");
			const jobSeekerRadioTag = document.getElementById("jobSeekerRadio");
			const errorTag = document.getElementById("errorMessage");
			if(employerRadioTag.checked){
				errorTag.style.display = "none";
				location.href = '/job-portal/employer/register.jsp';
			}
			else if (jobSeekerRadioTag.checked){
				errorTag.style.display = "none";
				location.href = "/job-portal/seeker/register.jsp?jobIDStr="+<%=jobIDStr%>;
			}
			else {
				errorTag.style.display = "block";
			}
		}
		const pwdEl = document.getElementById("floatingPassword");
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
	</script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
</body>
</html>