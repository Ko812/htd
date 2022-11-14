<%@ page import="java.util.*"%>
<%@ page import="com.ncs.model.*"%>
<% 
	Job job = (Job) session.getAttribute("job-to-edit");
	String role = job.getRole();
	String desc = job.getDesc();
	int salary = job.getSalary();
	int yearsOfExp = job.getYears_of_exp();
%>
<div class="emp-main-content">
	<form class="form-max-width border rounded-3 bg-light"
		action="/job-portal/EditJobPosting">
		<div class="form-title">Update Job Posting</div>
		<div class="form-group emp-form-row-style">
			<label for="floatingJobRole" class="form-label">Job Role</label>
			<input type="text" name="jobRole" class="form-control"
				placeholder="Job Role" id="floatingJobRole"
				value="<%=role%>" maxlength="20">
			<div id="invalidJobRoleMessageTag" style="display:none"><small style="color: red">Job role must be less than 20 characters.</small></div>
		</div>
		<div class="form-group emp-form-row-style">
			<label for="floatingJobDescription" class="form-label">Job Description</label>
			<textarea name="jobDescription" class="form-control" style="text-align: start"
				placeholder="Job Description" id="floatingJobDescription" rows="4"
				><%=desc%></textarea>
			<div id="invalidJobDescriptionMessageTag" style="display:none"><small style="color: red">Job description must be at least 40 characters and at most 200 characters in length.</small></div>
		</div>
		<div class="form-group emp-form-row-style">
			<label for="floatingSalary" class="form-label">Annual Salary (SGD)</label>
			<input type="number" name="salary" class="form-control"
				placeholder="Salary" id="floatingSalary"
				value="<%=salary%>" min=0>
		</div>
		<div class="form-group emp-form-row-style">
			<label for="floatingYearsOfExp" class="form-label">Years of Experience</label>
			<input type="number" name="yearsOfExperience" class="form-control"
				placeholder="Years of Experience" id="floatingYearsOfExp"
				value="<%=yearsOfExp%>" min=0>
		</div>
		<%
			String formMessage = (String) session.getAttribute("edit-job-posting-form-message");
			Boolean formMessageRead = (Boolean) session.getAttribute("form-message-read");
			if(formMessage != null && !formMessageRead){
				session.setAttribute("form-message-read", Boolean.parseBoolean("true"));
			%>
				<div class="form-message" style="color: red">
					<%=formMessage%>
				</div>
		<%}%>
		<div class="row emp-form-row-style">
			<div class="row-item">
				<button type="button" onclick="back()" class="login-button">Back</button>
			</div>
			<div class="row-item">
				<button type="submit" class="login-button">Save</button>
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
			if(event.target.value.length == 20){
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