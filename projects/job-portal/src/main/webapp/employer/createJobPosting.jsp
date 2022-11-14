<%@ page import="java.util.*"%>
<%@ page import="com.ncs.model.*"%>
<% 
	EmployerCompany ec = (EmployerCompany) session.getAttribute("logged-in-employer");
	String companyName = ec.getCompanyName();
	String userName = ec.getUserName();
	String companyEmail = ec.getCompanyEmail();
	String companyDetails = ec.getCompanyDetails();
	int yearsOfOperation = ec.getYearsOfOperation();
	String vmv = ec.getVmv();
	String companyWebsite = ec.getCompanyWebsite();
	CompanySize companySize = ec.getCompanySize();
%>
<script>
		const = clear () => {
			console.log("clearing ...");
			const jr = document.getElementById("floatingJobRole");
			const jd = document.getElementById("floatingJobDescription");
			const js = document.getElementById("floatingSalary");
			const jy = document.getElementById("floatingYearsOfExp");
			jr.value = "";
			jd.innerHTML = "";
			js.value = 0;
			jy.value = 0;
		}
		const validateAndSubmit=()=>{
			// No validation yet.
			location.href = '/job-portal/CreateJobPosting';
		}
</script>
<div class="emp-main-content">
	<form class="form-max-width border rounded-3 bg-light"
		action="/job-portal/CreateJobPosting">
		<div class="form-title">Create Job Posting</div>
		<div class="form-group emp-form-row-style">
			<label for="floatingJobRole" class="form-label">Job Role</label>
			<input type="text" name="jobRole" class="form-control"
				placeholder="Job Role" id="floatingJobRole" maxlength="20">
			<div id="invalidJobRoleMessageTag" style="display:none"><small style="color: red">Job role must not be empty or be less than 20 characters.</small></div>
		</div>
		<div class="form-group emp-form-row-style">
			<label for="floatingJobDescription" class="form-label">Job Description</label>
			<textarea name="jobDescription" class="form-control" maxlength="200"
				placeholder="Job Description" id="floatingJobDescription" rows="4"></textarea>
			<div id="invalidJobDescriptionMessageTag" style="display:none"><small style="color: red">Job description must be at least 40 characters and at most 200 characters in length.</small></div>
		</div>
		<div class="form-group emp-form-row-style">
			<label for="floatingSalary" class="form-label">Annual Salary (SGD)</label>
			<input type="number" name="salary" class="form-control"
				placeholder="Salary" id="floatingSalary" min=0>
		</div>
		<div class="form-group emp-form-row-style">
			<label for="floatingYearsOfExp" class="form-label">Years of Experience</label>
			<input type="number" name="yearsOfExperience" class="form-control"
				placeholder="Years of Experience" id="floatingYearsOfExp" min=0>
		</div>
		<%
			String formMessage = (String) session.getAttribute("create-job-posting-form-message");
			Boolean formMessageRead = (Boolean) session.getAttribute("form-message-read");
			if(formMessage != null && !formMessageRead){
				session.setAttribute("form-message-read", Boolean.parseBoolean("true"));
			%>
				<div class="form-message" style="color: red" id="createJobPostingFormMessage">
					<%=formMessage%>
				</div>
		<%}%>
		<div class="row emp-form-row-style">
			<div class="row-item">
				<button type="button" onclick="back()" class="login-button">Back</button>
			</div>
			<div class="row-item">
				<button type="submit" class="login-button">Post</button>
			</div>
		</div>
	</form>
	<script>
		const back = () => {
			history.back();
		}
		const jobRoleEl = document.getElementById("floatingJobRole");
		const jobDescEl = document.getElementById("floatingJobDescription");
		const invalidJobRoleMessageEl = document.getElementById("invalidJobRoleMessageTag");
		const invalidjobDescMessageEl = document.getElementById("invalidJobDescriptionMessageTag");
		jobRoleEl.addEventListener("keyup", (event)=>{
			if(event.target.value.length == 0 || event.target.value.length >= 20){
				invalidJobRoleMessageEl.style.display = "block";
				return;
			}
			invalidJobRoleMessageEl.style.display = "none";
		});
		jobDescEl.addEventListener("keyup", (event)=>{
			if(event.target.value.length < 40 || event.target.value.length > 200){
				invalidjobDescMessageEl.style.display = "block";
				return;
			}
			invalidjobDescMessageEl.style.display = "none";
		});
	</script>
	
</div>
