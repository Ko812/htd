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
			<form class="p-4 p-md-5 border rounded-3 bg-light"
				action="/job-portal/ResetPassword">
				<div class="form-title">Reset Password</div>
				<div class="form-floating mb-3">
					<input type="password" name="newPassword" class="form-control"
						placeholder="New password" id="floatingNewPassword"> <label
						for="floatingNewPassword">New password</label>
				</div>
				<div class="form-floating mb-3">
					<input type="password" name="cfmPassword" class="form-control"
						placeholder="Confirm Password" id="floatingCfmPassword"> <label
						for="floatingCfmPassword">Confirm password</label>
					<input type="password" name="sessionCode" style="display:none" value="<%=(String) session.getAttribute("sessionCode")%>">
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
						<button type="button" onclick="back()" class="btn btn-primary">Return</button>
					</div>
					<div class="row-item">
						<input type="submit" value="Reset" class="btn btn-primary">
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