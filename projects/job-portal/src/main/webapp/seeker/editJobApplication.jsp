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
	
	JobApplication app = (JobApplication) session.getAttribute("app-to-edit");
	String companyName, role, desc, companyEmail;
	Integer salary, yearsOfExpRq, jobId;
	if(app != null){
		jobId = app.getId();
		companyName = app.getCompanyName();
		role = app.getJobRole();
		desc = app.getJobDesc();
		salary = app.getSalary();
		yearsOfExpRq = app.getYearsOfExp();
		companyEmail = app.getCompanyEmail();
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
	<div class="detailed-job-item">
		<div class="job-item-button">
			<div class="company-logo">C</div>
			<div class="job-info-column">
				<div class="company-name job-meta">
					<%=companyName%></div>
				<div class="job-role job-meta">
					<%=role%></div>
				<div class="job-yoe job-meta">
					At least
					<%=yearsOfExpRq%>
					years
				</div>
				<div class="company-email job-meta">
					<%=companyEmail%>
				</div>
			</div>
			<div class="job-salary job-meta">
				<span> <%=salary%><br>SGD/MTH</span>
			</div>
		</div>
	</div>
	<form class="form-max-width border rounded-3 bg-light"
		action="/job-portal/CreateJobApplication">
		<div class="form-title">Edit to Application</div>
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
			String formMessage = (String) session.getAttribute("edit-app-form-message");
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
				<input type="submit" value="Edit" class="login-button edit-job-form-button">
			</div>
		</div>
	</form>
	<br><br><br><br><br><br>
</div>