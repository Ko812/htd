<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Land A Job - Forget Password</title>
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
		<div class="body-base">
			<div class="background-hero"></div>
		</div>
		<div class="body-container">
			<form class="p-4 p-md-5 border rounded-3 bg-light"
				action="/job-portal/ResetPassword">
				<div class="form-title">Reset Password</div>
				<div class="form-floating mb-3">
					<input type="password" name="newPassword" class="form-control"
						placeholder="New password" id="floatingNewPassword" required>
					<label for="floatingNewPassword">New password</label>
					<i class="bi bi-eye-slash" id="togglePasswordVis" style="font-size: 20px; position: absolute; top: 12px; right: 15px; cursor: pointer;"></i>
					<i class="bi bi-eye" id="togglePasswordInvis" style="font-size: 20px; position: absolute; top: 12px; right: 15px; cursor: pointer; display:none;"></i>
					<small id="password-req-message" style="display:none; color:red;">Must be at least 8 characters long, contain <br>at least 1 numeric digit, 1 capital letter, 1 small <br>letter and 1 special character.</small>
				</div>
				<div class="form-floating mb-3">
					<input type="password" name="cfmPassword" class="form-control"
						placeholder="Confirm Password" id="floatingCfmPassword" required>
					<label for="floatingCfmPassword">Confirm password</label>
					<i class="bi bi-eye-slash" id="toggleCfmPasswordVis" style="font-size: 20px; position: absolute; top: 12px; right: 15px; cursor: pointer;"></i>
					<i class="bi bi-eye" id="toggleCfmPasswordInvis" style="font-size: 20px; position: absolute; top: 12px; right: 15px; cursor: pointer; display:none;"></i>
					<input type="password" name="sessionCode" style="display:none" value="<%=(String) session.getAttribute("sessionCode")%>">
					<input type="text" name="loginAs" value="<%=request.getParameter("loginAs")%>" style="display:none">
					<input type="text" name="email" value="<%=request.getParameter("email")%>" style="display:none">
				</div>
				<%
				String formMessage = (String) session.getAttribute("reset-password-form-message");
				Boolean formMessageRead = (Boolean) session.getAttribute("form-message-read");
				if (formMessage != null && !formMessageRead) {
					session.setAttribute("form-message-read", Boolean.parseBoolean("true"));
				%>
				<div class="form-message">
					<%=formMessage%>
				</div>
				<%}%>
				<div class="row">
					<div class="row-item">
						<button type="reset" class="login-button">Clear</button>
					</div>
					<div class="row-item">
						<input type="submit" value="Change" class="login-button">
					</div>
				</div>
			</form>
		</div>
	</div>
	<script src="validation.js" type="text/javascript"></script>
	<script>
		const back = () => {
			history.back();
		}
		const pwdEl = document.getElementById("floatingNewPassword");
		const cfmPwdEl = document.getElementById("floatingCfmPassword");
		const invalidPasswordMessage = document.getElementById("password-req-message");
		pwdEl.addEventListener("keyup", (event) => {
			if(!validPasswordWithSpecialChar(event.target.value)){
				invalidPasswordMessage.style.display = "block";
				return;
			}
			invalidPasswordMessage.style.display = "none";
		});
		const togglePasswordVis = document
	    .querySelector('#togglePasswordVis');
		const togglePasswordInvis = document
	    .querySelector('#togglePasswordInvis');
		const toggleCfmPasswordVis = document
	    .querySelector('#toggleCfmPasswordVis');
		const toggleCfmPasswordInvis = document
	    .querySelector('#toggleCfmPasswordInvis');

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
		
		toggleCfmPasswordVis.addEventListener('click', () => {
			
		    // Toggle the type attribute using
		    // getAttribure() method
		          
		    cfmPwdEl.setAttribute('type', 'text');
		
		    // Toggle the eye and bi-eye icon
		    toggleCfmPasswordVis.style.display = "none";
		    toggleCfmPasswordInvis.style.display = "inline";
		});
		
		toggleCfmPasswordInvis.addEventListener('click', () => {
			
		    // Toggle the type attribute using
		    // getAttribure() method
		          
		    cfmPwdEl.setAttribute('type', 'password');
		
		    // Toggle the eye and bi-eye icon
		    toggleCfmPasswordVis.style.display = "inline";
		    toggleCfmPasswordInvis.style.display = "none";
		});
	</script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
</body>
</html>