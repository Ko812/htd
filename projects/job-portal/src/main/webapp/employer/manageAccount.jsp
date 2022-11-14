<%@ page import="java.util.*"%>
<%@ page import="com.ncs.model.*"%>
<% 
	EmployerCompany ec = (EmployerCompany) session.getAttribute("logged-in-employer");
	String companyName = ec.getCompanyName();
	String userName = ec.getUserName();
	String companyEmail = ec.getCompanyEmail();
	String companyDetails = ec.getCompanyDetails();
	if(companyDetails == null){
		companyDetails = "";
	}
	int yearsOfOperation = ec.getYearsOfOperation();
	String vmv = ec.getVmv();
	if(vmv == null){
		vmv = "";
	}
	String companyWebsite = ec.getCompanyWebsite();
	if(companyWebsite == null){
		companyWebsite = "";
	}
	CompanySize companySize = ec.getCompanySize();
%>
<div class="emp-main-content">
	<form class="form-max-width border rounded-3 bg-light"
		action="/job-portal/ManageCompanyDetails" id="emp-details-form">
		<div class="form-title">Manage Account</div>
		<div class="form-group emp-form-row-style">
			<label for="floatingCompanyName">Company Name</label>
			<input type="text" name="companyName" class="form-control"
				placeholder="Company Name" id="floatingCompanyName"
				value="<%=companyName%>">
		</div>
		<div class="form-group emp-form-row-style">
			<label for="floatingUserName">User name</label>
			<input type="text" name="userName" class="form-control"
				placeholder="User name" id="floatingUserName"
				value="<%=userName%>"> 
		</div>
		<div class="form-group emp-form-row-style">
			<label for="floatingCompanyEmail">Company Email</label>
			<input type="text" name="companyEmail" class="form-control"
				placeholder="Company Email" id="floatingCompanyEmail"
				value="<%=companyEmail%>">
		</div>
		<div class="form-group emp-form-row-style">
			<label for="floatingCompanyDetails">Company Details</label>
			<textarea name="companyDetails" class="form-control"
				placeholder="Company Details" id="floatingCompanyDetails"><%=companyDetails%></textarea>
		</div>
		<div class="form-group emp-form-row-style">
			<label for="floatingYearsOfOperation">Years Of Operation</label>
			<input type="number" name="yearsOfOperation" class="form-control"
				placeholder="Years of Operation" id="floatingYearsOfOperation"
				value="<%=yearsOfOperation%>" min=0>
		</div>
		<div class="form-group emp-form-row-style">
			<label for="floatingCompanyVMV">VMV</label>
			<input type="text" name="companyVMV" class="form-control"
				placeholder="Company VMV" id="floatingCompanyVMV"
				value="<%=vmv%>">
			
		</div>
		<div class="form-group emp-form-row-style">
			<label for="floatingCompanyWebsite">Website</label>
			<input type="text" name="companyWebsite" class="form-control"
				placeholder="Company Website" id="floatingCompanyWebsite"
				value="<%=companyWebsite%>">
			
		</div>
		<div class="form-group emp-form-row-style">
			<label for="floatingCompanySize">Company Size</label>
			<select name="companySize" class="form-control"
				 id="floatingCompanySize">
				 <option value="<%=CompanySize.Small%>" <%=companySize==CompanySize.Small?"selected":""%>>Small (1-10)</option>
				 <option value="<%=CompanySize.SmallMedium%>" <%=companySize==CompanySize.SmallMedium?"selected":""%>>SmallMedium (11-50)</option>
				 <option value="<%=CompanySize.Medium%>" <%=companySize==CompanySize.Medium?"selected":""%>>Medium (51-100)</option>
				 <option value="<%=CompanySize.MediumLarge%>" <%=companySize==CompanySize.MediumLarge?"selected":""%>>MediumLarge (101-500)</option>
				 <option value="<%=CompanySize.Large%>" <%=companySize==CompanySize.Large?"selected":""%>>Large (501-1000)</option>
				 <option value="<%=CompanySize.Transnational%>" <%=companySize==CompanySize.Transnational?"selected":""%>>Trans-National (Has at least 1 overseas operation)</option>
				 <option value="<%=CompanySize.Multinational%>" <%=companySize==CompanySize.Multinational?"selected":""%>>Multi-National (Has several overseas operation)</option>
			</select>
		</div>
		<%
			String formMessage = (String) session.getAttribute("edit-employer-account-form-message");
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
				<button type="submit" form="emp-details-form" class="login-button">Save</button>
			</div>
		</div>
	</form>
	<script>
		const back = () => {
			history.back();
		}
	</script>
		
</div>
<br><br><br><br><br>
