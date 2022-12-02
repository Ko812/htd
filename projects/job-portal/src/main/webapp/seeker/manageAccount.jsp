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
		action="/job-portal/ManageJobSeekerDetails" id="js-details-form">
		<div class="form-title">Manage Account</div>
		<div class="form-name-row emp-form-row-style">
		<div class="form-group form-name-group">
			<label for="floatingFirstName" class="form-label">First Name</label>
			<input type="text" name="firstName" class="form-control"
				placeholder="First Name" id="floatingFirstName"
				value="<%=firstName%>" required maxlength="50">
			<div id="firstNameInvalidMessage" style="display:none"><small style="color: red">First name cannot contain digits.</small></div>
		</div>
		<div class="form-group form-name-group">
			<label for="floatingLastName" class="form-label">Last Name</label>
			<input type="text" name="lastName" class="form-control"
				placeholder="Last name" id="floatingLastName"
				value="<%=lastName%>" required maxlength="50"> 
			<div id="lastNameInvalidMessage" style="display:none"><small style="color: red">Last name cannot contain digits.</small></div>
		</div>
		</div>
		<div class="form-group emp-form-row-style">
			<label for="floatingUserName" class="form-label">User Name</label>
			<input type="text" name="userName" class="form-control"
				placeholder="User Name" id="floatingUserName"
				value="<%=userName%>" required maxlength="50">
			<div id="usernameInvalidMessage" style="display:none"><small style="color: red">User name can only contain alphanumeric characters.</small></div>
		</div>
		<div class="form-group emp-form-row-style">
			<label for="floatingEmail" class="form-label">Email</label>
			<input type="text" name="email" class="form-control"
				placeholder="Email" id="floatingEmail"
				value="<%=email%>" required maxlength="50">
			<small id="emailValidationMessage" style="display:none; color:red;">Email address not in a valid format.</small>
		</div>
		<div class="form-group emp-form-row-style">
			<label for="floatingNRIC" class="form-label">Identification Number</label>
			<input type="text" name="NRIC" class="form-control"
				placeholder="Identification Number" id="floatingNRIC"
				value="<%=NRIC%>" min=0 required maxlength="9">
			<div id="nricInvalidMessage" style="display:none"><small style="color: red">NRIC format is invalid.</small></div>
			<div id="nricInvalidMessage2" style="display:none"><small style="color: red">NRIC is invalid.</small></div>
		</div>
		<div class="form-group emp-form-row-style">
			<label for="floatingContact" class="form-label">Contact number</label>
			<input type="text" name="contact" class="form-control"
				placeholder="Contact number" id="floatingContact"
				value="<%=contact%>" required maxlength="8">
			<div id="contactInvalidMessage" style="display:none"><small style="color: red">Contact should have 8 digits.</small></div>
		</div>
		<%
			String formMessage = (String) session.getAttribute("update-js-form-message");
			Boolean formMessageRead = (Boolean) session.getAttribute("form-message-read");
			if(formMessage != null && !formMessageRead){
				session.setAttribute("form-message-read", Boolean.parseBoolean("true"));
			%>
				<div class="form-message">
					<%=formMessage%>
				</div>
		<%}%>
	</form>
	<div class="row emp-form-row-style">
		<div class="row-item">
			<button type="button" name="delete" onclick="deleteAcc('<%=js.getNumberOfJobsApplied()%>')" class="login-button">Delete</button>
		</div>
		<div class="row-item">
			<button form="js-details-form" type="submit" class="login-button">Save</button>
		</div>
	</div>
	<script src="../validation.js"></script>
	<script>
		const deleteAcc = (noOfJobsApplied)=>{
			if(noOfJobsApplied > 0){
				if(confirm("You have job applications. Delete all applications and account?")){
					location.href="/job-portal/DeleteJSAccount";	
					return;
				}
				else {
					return;	
				}
			}
			else if(confirm("Confirm delete account?")){
				location.href="/job-portal/DeleteJSAccount";
				return;
			}
			else {
				return;
			}
		}
		
		const firstNameInput = document.getElementById("floatingFirstName");
		const lastNameInput = document.getElementById("floatingLastName");
		const firstNameValidationMessage = document.getElementById("firstNameInvalidMessage");
		const lastNameValidationMessage = document.getElementById("lastNameInvalidMessage");
		const usernameEl = document.getElementById("floatingUserName");
		const usernameValidationMessage = document.getElementById("usernameInvalidMessage");
		
		const emailEl = document.getElementById("floatingEmail");
		const invalidEmailMessage = document.getElementById("emailValidationMessage");
		
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
		
		emailEl.addEventListener("change", (event) => {
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
		ensureValidation("js-details-form", 
				[
					nameValidator,
					nameValidator,
					isAngloTextWithDigits,
					validEmail,
					contactValidator,
					NRICValidator,
					],
				[
					"floatingFirstName",
					"floatingLastName",
					"floatingUserName",
					"floatingEmail",
					"floatingContact",
					"floatingNRIC",
				]
		);
	</script>
</div>
