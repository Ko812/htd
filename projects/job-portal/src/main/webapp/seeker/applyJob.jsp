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
	int yearsOfExp = js.getYearsOfExperience();
	List<Acad> acads = new ArrayList<Acad>();
	if(js.getAcadDetails() != null){
		acads = js.getAcadDetails();
	}
	List<String> workExps = new ArrayList<String>();
	if(js.getWorkExp() != null){
		workExps = js.getWorkExp();
	}
	List<String> awards = new ArrayList<String>();
	if(js.getAwards() != null){
		awards = js.getAwards();
	}
	
	Job jobToApply = (Job) session.getAttribute("job-to-apply");
	String companyName, role, desc, companyEmail;
	Integer salary, yearsOfExpRq, jobId;
	if(jobToApply != null){
		jobId = jobToApply.getId();
		companyName = jobToApply.getCompany_name();
		role = jobToApply.getRole();
		desc = jobToApply.getDesc();
		
		desc = desc.replace("\n", "<br>");
		
		salary = jobToApply.getSalary();
		yearsOfExpRq = jobToApply.getYears_of_exp();
		companyEmail = jobToApply.getCompany_email();
	}
	else {
		companyName = "";
		role = "";
		desc = "";
		companyEmail = "";
		yearsOfExpRq=null;
		salary=null;
	}
	
%>
<div class="emp-main-content">
	<div class="detailed-job-info-box">
		<div class="detailed-company-logo-box">
			<div class="detailed-company-logo-1"></div>
		</div>
		<div class="detailed-job-info-column">
			<div class="detailed-major-job-meta-box">
				<div class="detailed-major-job-meta">
					<%=companyName%>
				</div>
				<div class="detailed-minor-job-meta">
					<%=companyEmail%>
				</div>
			</div>
			<div class="detailed-minor-job-meta-box">
				<div class="detailed-minor-job-meta detailed-job-meta-spacer detailed-job-meta-italicise">
					<%=role%>&nbsp;(min. <%=yearsOfExpRq%> years exp.)
				</div>
				<div class="detailed-minor-job-meta">
					<%=desc%>
				</div>
				<br>
				<div class="detailed-minor-job-meta">
					<span>Annual salary: <%=salary%> SGD</span>
				</div>
			</div>
		</div>
	</div>
	<form class="form-max-width border rounded-3 bg-light"
		action="/job-portal/CreateJobApplication">
		<div class="form-title">Apply to Job</div>
		<div class="form-group form-name-group">
			<label for="floatingName" class="form-label">Name</label><%=lastName%>&nbsp;<%=firstName%>
		</div>
		<div class="form-group emp-form-row-style">
			<label for="floatingEmail" class="form-label">Email</label><%=email%>
		</div>
		<div class="form-group emp-form-row-style">
			<label for="floatingNRIC" class="form-label">Identification Number</label><%=NRIC%>
		</div>
		<div class="form-group emp-form-row-style">
			<label for="floatingContact" class="form-label">Contact number</label><%=contact%>
		</div>
		<div class="form-group emp-form-row-style">
			<label for="floatingYearsOfExp" class="form-label">Years of Experience</label><%=yearsOfExp%>
		</div>
		
		<div id="acadFormInputs" class="form-subsection">
			<div class="form-sub-title">Academic</div>
			<%
				for(int i=1;i<=acads.size();i++){
					Acad acad = acads.get(i-1);
					%>
			<div class="form-group emp-form-row-style">
				<label for="floatingAcademic<%=i%>" class="form-label">Academic Credential <%=i%></label><%=acad%>
			</div>
			<%}%>
		</div>
		<div id="workExpFormInputs" class="form-subsection">
			<div class="form-sub-title">Work Experience</div>
			<%
				for(int i=1;i<=workExps.size();i++){
			%>
			<div class="form-group emp-form-row-style">
				<label for="floatingWorkExp<%=i%>" class="form-label">Work Experience <%=i%></label>
				<span id="floatingWorkExp<%=i%>"><%=workExps.get(i-1)%></span>
			</div>
		<%}%>
		</div>
		<div id="awardFormInputs" class="form-subsection">
			<div class="form-sub-title">Awards</div>
			<%
				for(int i=1;i<=awards.size();i++){
			%>
				<div class="form-group emp-form-row-style">
					<label for="floatingAward<%=i%>" class="form-label">Award <%=i%></label><%=awards.get(i-1)%>
				</div>
			<%}%>
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
		<div class="row emp-form-row-style">
			<div class="button-row">
				<button type="button" onclick="location.href='/job-portal/seeker/jobSeekerDashboard.jsp?currentView=add-credentials'" class="login-button edit-job-form-button">Add Credentials</button>
				<input type="submit" value="Apply now" class="login-button edit-job-form-button">
			</div>
		</div>
	</form>
	<br><br><br><br><br><br>
</div>