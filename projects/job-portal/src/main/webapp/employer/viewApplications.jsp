<%@ page import="java.util.*"%>
<%@ page import="com.ncs.model.*"%>
<% 
	@SuppressWarnings("unchecked")
	List<Job> jobs = (ArrayList<Job>)session.getAttribute("jobs");
	@SuppressWarnings("unchecked")
	List<JobApplication> apps = (ArrayList<JobApplication>)session.getAttribute("filtered-apps");
	String filterRole = (String) session.getAttribute("filterRole");
	String filterDatePosted = (String) session.getAttribute("filterDatePosted");
	String filterByMeetExpRqmt = (String) session.getAttribute("filterByMeetExpRqmt");
	if(filterRole == null || filterRole.equals("null")) {
		filterRole = "all";
	}
	if(filterDatePosted == null || filterDatePosted.equals("null")) {
		filterDatePosted = "all";
	}
	if(filterByMeetExpRqmt == null || filterByMeetExpRqmt.equals("null")) {
		filterByMeetExpRqmt = "false";
	}
	String searchField = (String) session.getAttribute("searchField");
	if(searchField == null){
		searchField = "";
	}
%>
<div class="emp-main-content">
	<div class="form-title">View Job Applicants</div>
	<div class="search-form-container">
		<form class="form-90-width border rounded-3 bg-light"
			action="/job-portal/ViewJobApplications">
			<div class="search-field-box">
				<input type="text" class="search-field" name="searchJobApplications" list="jobRolesDataList"
					placeholder="Search applications" value="<%=searchField%>">
				<datalist id="jobRolesDataList">
	                <option value="FullStack Developer"></option>
	                <option value="Java Developer"></option>
	                <option value="Software Developer"></option>
	                <option value="Software Engineer"></option>
	                <option value="App Developer"></option>
	                <option value="Front-end Developer"></option>
	                <option value="Back-end Developer"></option>
	                <option value="Database Engineer"></option>
	                <option value="Database Administrator"></option>
	                <option value="Cyber-security Manager"></option>
	                <option value="Cyber-security Executive"></option>
            	</datalist>
				<button type="submit" class="search-button">&#128269;</button>
			</div>
			<div class="search-option-box">
				<div class="form-group filter-item">
					<label for="filterBy" class="form-label">Filter by Job Role:</label> <select
						name="filterByJobRole" id="filterByJobRole">
						<option value="all" selected>All job postings</option>
						<%
						Set<String> postedJobRoles = new HashSet<String>();
						for (Job j : jobs) {
							postedJobRoles.add(j.getRole());
						}
						%>
						<%
						for (String jr : postedJobRoles) {
						%>
						<option value="<%=jr%>" <%=filterRole.equals(jr) ? "selected" : ""%>><%=jr%></option>
						<%
						}
						%>
					</select>
				</div>
				<div class="form-group filter-item">
					<label for="filterBy" class="form-label">Filter by Date Posted:</label> <select
						name="filterByDatePosted" id="filterByDatePosted">
						<option value="all"
							<%=filterDatePosted.equals("all") ? "selected" : ""%>>All
							posted</option>
						<option value="last1Month"
							<%=filterDatePosted.equals("last1Month") ? "selected" : ""%>>Posted
							Last Month</option>
						<option value="last3Month"
							<%=filterDatePosted.equals("last3Month") ? "selected" : ""%>>Posted
							Last 3 Month</option>
						<option value="last6Month"
							<%=filterDatePosted.equals("last6Month") ? "selected" : ""%>>Posted
							Last 6 Month</option>
					</select>
				</div>
				<div class="form-group filter-item layout-horizontal">
					<input type="checkbox" name="filterByMeetExpRqmt" value="meetExperienceRequirement"
						id="filterMeetExpCriterion" <%=filterByMeetExpRqmt.equals("true")?"checked":""%>> 
					<label for="filterByMeetExpRqmt" class="form-label-margin-left">Meet experience requirement</label>
				</div>
			</div>
			<input type="text" name="viewBy" value="search" style="display:none; width:0px;height:0px">
		</form>
	</div>
	<table>
		<thead>
		<tr>
			<th>Application ID</th> <!-- 1 -->
			<th>Job Role</th><!-- 2 -->
			<th>Applicant Name</th><!-- 3 -->
			<th>Exp. Required</th><!-- 4 -->
			<th>Applicant Years of Exp.</th><!-- 5 -->
			<th>Applicant Contact</th><!-- 6 -->
			<th>Applicant Email</th><!-- 7 -->
			<th>Applicant Iden. No.</th><!-- 8 -->
			<th>Credential 1</th><!-- 9 -->
			<th>Credential 2</th><!-- 10 -->
			<th>Credential 3</th><!-- 11 -->
			<th>Date of Application</th><!-- 12 -->
			<th>Status</th><!-- 13 -->
			<th></th><!-- 14 -->
			<th></th>
		</tr>
		</thead>
		<tbody>
			<%
				if(apps != null){	
				for(JobApplication app : apps ){
					ApplicationStatus currentStatus = app.getStatus();
					boolean disableUpdateStatusButton = currentStatus == ApplicationStatus.JobSeekerAccepted || currentStatus == ApplicationStatus.JobSeekerDeclined;
					String cred1, cred2, cred3;
					if(app.getApplicationCredential() != null && !app.getApplicationCredential().isEmpty()){
						cred1 = app.getApplicationCredential().get(0);
						if(app.getApplicationCredential().size()>1){
							cred2 = app.getApplicationCredential().get(1);
							if(app.getApplicationCredential().size()>2){
								cred3 = app.getApplicationCredential().get(2);
							}
							else{
								cred3 = "";
							}
						}
						else{
							cred2 = "";
							cred3 = "";
						}
					}
					else{
						cred1 = "";
						cred2 = "";
						cred3 = "";
					}
				%>
			<tr>
				<td><%=app.getId()%></td><!-- 1 -->
				<td><%=app.getJobRole()%></td><!-- 2 -->
				<td><%=app.getJsFirstName() + " " + app.getJsLastName()%></td><!-- 3 -->
				<td><%=app.getYearsOfExp()%> years</td><!-- 4 -->
				<td><%=app.getJsYearsOfExp()%></td><!-- 5 -->
				<td><%=app.getJsContact()%></td><!-- 6 -->
				<td><%=app.getJsEmail()%></td><!-- 7 -->
				<td><%=app.getJsIdentificationNumber()%></td><!-- 8 -->
				<td><%=cred1%></td><!-- 9 -->
				<td><%=cred2%></td><!-- 10 -->
				<td><%=cred3%></td><!-- 11 -->
				<td><%=app.getApplicationDate()%></td><!-- 12 -->
				<td>
					<%
						if(currentStatus==ApplicationStatus.JobSeekerAccepted || currentStatus==ApplicationStatus.JobSeekerDeclined){
							%>
							<div><%=currentStatus.toString()%></div>
					<%	}
						else {
					%>	
					<select name="appStatus<%=app.getId()%>" id="<%=app.getId()%>"><!-- 13 -->
						<option value="<%=ApplicationStatus.Created%>" <%=currentStatus == ApplicationStatus.Created ? "selected" : ""%>>Created</option>
						<option value="<%=ApplicationStatus.Sent%>" <%=currentStatus == ApplicationStatus.Sent ? "selected" : ""%>>Sent</option>
						<option value="<%=ApplicationStatus.Received%>" <%=currentStatus == ApplicationStatus.Received ? "selected" : ""%>>Received</option>
						<option value="<%=ApplicationStatus.Processing%>" <%=currentStatus == ApplicationStatus.Processing ? "selected" : ""%>>Processing</option>
						<option value="<%=ApplicationStatus.PendingInterview%>" <%=currentStatus == ApplicationStatus.PendingInterview ? "selected" : ""%>>Pending Interview</option>
						<option value="<%=ApplicationStatus.EmployerAccepted%>" <%=currentStatus == ApplicationStatus.EmployerAccepted ? "selected" : ""%>>Employer Accepted</option>
						<option value="<%=ApplicationStatus.EmployerDeclined%>" <%=currentStatus == ApplicationStatus.EmployerDeclined ? "selected" : ""%>>Employer Declined</option>
						<option value="<%=ApplicationStatus.JobConfirmed%>" <%=currentStatus == ApplicationStatus.JobConfirmed ? "selected" : ""%>>Job confirmed</option>
					</select>
					<% }%>
				</td>
				<td>
					<button type="button" onclick="updateStatus('<%=app.getId()%>')" class="login-button update-status-button" <%=disableUpdateStatusButton?"disabled":"" %>>Update Status</button>
				</td><!-- 14 -->
				<td>
					<button type="button" onclick="confirmJob('<%=app.getId()%>')" class="login-button update-status-button" <%=disableUpdateStatusButton?"":"disabled" %>>Confirm Job</button>
				</td>
			</tr>
			<%}
		}%>
		
		<%
			if(apps == null || apps.isEmpty()){
				%>
				<tr><td>No applications found.</td></tr>
		<%}%>
		</tbody>
	</table>
	<script>
		const confirmJob = (appID) => {
			location.href = "/job-portal/EMUpdateApplicationStatus?appID="+appID+"&updateStatus=JobConfirmed";
		}
		const updateStatus = (appID) => {
			const statusTag = document.getElementById(appID);
			const statusVal = statusTag.value;
			location.href = "/job-portal/EMUpdateApplicationStatus?appID="+appID+"&updateStatus="+statusVal;
		}
		function filterJobs(role,datePosted,meetExpReq){
			location.href = "/job-portal/ViewJobApplications?viewBy=filter&filterRole="+role+"&filterDatePosted="+datePosted+"&filterByMeetExpRqmt="+meetExpReq+"&jobID=9999";
		}
		const filterJobRoleTag = document.getElementById("filterByJobRole");
		const filterDatePostedTag = document.getElementById("filterByDatePosted");
		const filterMeetExpCriterionTag = document.getElementById("filterMeetExpCriterion");
		filterJobRoleTag.addEventListener("change", (event) => {
			let jobRoleFilterVal = event.target.value;
			let datePostedFilterVal = filterDatePostedTag.value;
			let meetExpReqFilterVal = filterMeetExpCriterionTag.checked;
			filterJobs(jobRoleFilterVal,datePostedFilterVal ,meetExpReqFilterVal);
		});
		
		filterDatePostedTag.addEventListener("change", (event) => {
			let jobRoleFilterVal = filterJobRoleTag.value;
			let datePostedFilterVal = event.target.value;
			let meetExpReqFilterVal = filterMeetExpCriterionTag.checked;
			filterJobs(jobRoleFilterVal,datePostedFilterVal ,meetExpReqFilterVal);
		});
		
		filterMeetExpCriterionTag.addEventListener("change", (event) => {
			let jobRoleFilterVal = filterJobRoleTag.value;
			let datePostedFilterVal = filterDatePostedTag.value;
			let meetExpReqFilterVal = event.target.checked;
			filterJobs(jobRoleFilterVal,datePostedFilterVal ,meetExpReqFilterVal);
		});
	</script>
	
</div>
<br><br><br><br><br>
