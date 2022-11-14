<%@ page import="java.util.*"%>
<%@ page import="com.ncs.model.*"%>
<% 
	JobSeeker js = (JobSeeker) session.getAttribute("logged-in-job-seeker");
	String firstName = js.getFirstName();
	String lastName = js.getLastName();
	String userName = js.getUserName();
	String email = js.getEmail();
	String NRIC = js.getNRIC();
	String contact = js.getContact();
	
%>
<div class="emp-main-content">
	<form class="form-max-width border rounded-3 bg-light"
		action="/job-portal/JSChangePassword">
		<div class="form-title">Reset Password</div>
		<div class="form-group emp-form-row-style">
			<label for="floatingOldPassword" class="form-label">Old password</label>
			<input type="password" name="oldPassword" class="form-control"
				placeholder="Old password" id="floatingOldPassword"> 
		</div>
		<div class="form-group emp-form-row-style">
			<label for="floatingNewPassword" class="form-label">New password</label>
			<input type="password" name="newPassword" class="form-control"
				placeholder="New password" id="floatingNewPassword"> 
		</div>
		<div class="form-group emp-form-row-style">
			<label for="floatingCfmPassword" class="form-label">Confirm password</label>
			<input type="password" name="cfmPassword" class="form-control"
				placeholder="Confirm Password" id="floatingCfmPassword"> 
		</div>
		<%
			String formMessage = (String) session.getAttribute("change-password-form-message");
			Boolean formMessageRead = (Boolean) session.getAttribute("form-message-read");
			if(formMessage != null && !formMessageRead){
				session.setAttribute("form-message-read", Boolean.parseBoolean("true"));
			%>
				<div class="form-message">
					<%=formMessage%>
				</div>
		<%}%>
		<div class="row emp-form-row-style">
			<div class="row-item">
				<button type="button" onclick="back()" class="login-button">Back</button>
			</div>
			<div class="row-item">
				<input type="submit" value="Update" class="login-button">
			</div>
		</div>
	</form>
	<script>
		const back = ()=>{
			history.back();
		}
	</script>
</div>
