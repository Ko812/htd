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
				value="<%=firstName%>">
			<div id="firstNameInvalidMessage" style="display:none"><small style="color: red">First name cannot contain digits.</small></div>
		</div>
		<div class="form-group form-name-group">
			<label for="floatingLastName" class="form-label">Last Name</label>
			<input type="text" name="lastName" class="form-control"
				placeholder="Last name" id="floatingLastName"
				value="<%=lastName%>"> 
			<div id="lastNameInvalidMessage" style="display:none"><small style="color: red">Last name cannot contain digits.</small></div>
		</div>
		</div>
		<div class="form-group emp-form-row-style">
			<label for="floatingUserName" class="form-label">User Name</label>
			<input type="text" name="userName" class="form-control"
				placeholder="User Name" id="floatingUserName"
				value="<%=userName%>">
		</div>
		<div class="form-group emp-form-row-style">
			<label for="floatingEmail" class="form-label">Email</label>
			<input type="text" name="email" class="form-control"
				placeholder="Email" id="floatingEmail"
				value="<%=email%>">
		</div>
		<div class="form-group emp-form-row-style">
			<label for="floatingNRIC" class="form-label">Identification Number</label>
			<input type="text" name="NRIC" class="form-control"
				placeholder="Identification Number" id="floatingNRIC"
				value="<%=NRIC%>" min=0>
			<div id="nricInvalidMessage" style="display:none"><small style="color: red">NRIC format is invalid.</small></div>
			<div id="nricInvalidMessage2" style="display:none"><small style="color: red">NRIC is invalid.</small></div>
		</div>
		<div class="form-group emp-form-row-style">
			<label for="floatingContact" class="form-label">Contact number</label>
			<input type="text" name="contact" class="form-control"
				placeholder="Contact number" id="floatingContact"
				value="<%=contact%>">
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
			<button type="button" name="delete" onclick="deleteAcc()" class="login-button">Delete</button>
		</div>
		<div class="row-item">
			<button form="js-details-form" type="submit" class="login-button">Save</button>
		</div>
	</div>
	<script>
		const deleteAcc = ()=>{
			if(confirm("Confirm delete account?")){
				location.href="/job-portal/DeleteJSAccount"
			}
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
</div>
