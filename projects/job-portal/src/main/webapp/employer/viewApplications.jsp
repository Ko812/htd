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
	String sortOption = (String) session.getAttribute("sortOption");
	if(sortOption == null){
		sortOption = "none";
	}
%>
<div class="emp-main-content">
	<div class="form-title">View Job Applicants</div>
	<div class="application-search-form-container">
		<form class="border rounded-3 bg-light form-100-width"
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
				<button type="submit" class="search-button"><i class="fa-solid fa-magnifying-glass"></i></button>
			</div>
			<div class="search-option-box">
				<div class="filter-item filter-label" style="font-size:18px; font-weight:bold;">
					Filter
				</div>
				<div class="form-group filter-item">
					<label for="filterBy" class="form-filter-label">By Job Role:</label> <select
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
					<label for="filterBy" class="form-filter-label">By Application Date:</label> <select
						name="filterByDatePosted" id="filterByDatePosted">
						<option value="all"
							<%=filterDatePosted.equals("all") ? "selected" : ""%>>All</option>
						<option value="last1Month"
							<%=filterDatePosted.equals("last1Month") ? "selected" : ""%>>Last Month</option>
						<option value="last3Month"
							<%=filterDatePosted.equals("last3Month") ? "selected" : ""%>>Last 3 Month</option>
						<option value="last6Month"
							<%=filterDatePosted.equals("last6Month") ? "selected" : ""%>>Last 6 Month</option>
					</select>
				</div>
				<div class="form-group filter-item layout-horizontal">
					<input type="checkbox" name="filterByMeetExpRqmt" value="meetExperienceRequirement"
						id="filterMeetExpCriterion" <%=filterByMeetExpRqmt.equals("true")?"checked":""%>> 
					<label for="filterByMeetExpRqmt" class="form-label-margin-left">Meet experience requirement</label>
				</div>
			</div>
			<input type="text" name="viewBy" value="search" style="display:none; width:0px;height:0px">
		<div class="sort-options-box">
		<div class="option-label" style="font-size:18px; font-weight:bold;">Sort by</div>
			<div class="sort-option">
				<input type="radio" name="sortOption" id="sortByAppExp" value="AppExp" <%=sortOption.equals("AppExp")?"checked":""%>>
				<label for="sortByAppExp" class="sort-option-label">Applicant<br>Experience</label>
			</div>
			<div class="sort-option">
				<input type="radio" name="sortOption" id="sortByAppDate" value="AppDate" <%=sortOption.equals("AppDate")?"checked":""%>>
				<label for="sortByAppDate" class="sort-option-label">Application<br>Date</label>
			</div>
			<div class="sort-option">
				<input type="radio" name="sortOption" id="sortByAppStatus" value="AppStatus" <%=sortOption.equals("AppStatus")?"checked":""%>>
				<label for="sortByAppStatus" class="sort-option-label">Application<br>Status</label>
			</div>
			<div class="sort-option">
				<input type="radio" name="sortOption" id="sortByAppCred" value="AppCred" <%=sortOption.equals("AppCred")?"checked":""%>>
				<label for="sortByAppCred" class="sort-option-label">Application<br>Credential</label>
			</div>
		</div>
		</form>
	</div>
	
	<form action="/job-portal/EMBatchStatusUpdate" method="post" class="border rounded-3 bg-light batch-update-form" id="batchStatusUpdateForm">
	<div class="batch-update-button-box">
		<button type="submit"class="login-button update-all-status-button" form="batchStatusUpdateForm">Update all<br>selected</button>
		<button type="button" onclick="updateSelectedAppsStatusTo('Received')" class="login-button update-all-status-button">Receive selected</button>
		<button type="button" onclick="updateSelectedAppsStatusTo('Processing')" class="login-button update-all-status-button">Process selected</button>
		<button type="button" onclick="updateSelectedAppsStatusTo('PendingInterview')" class="login-button update-all-status-button">Interview selected</button>
		<button type="button" onclick="updateSelectedAppsStatusTo('EmployerDeclined')" class="login-button update-all-status-button">Decline selected</button>
	</div>
	<div style="overflow-x:auto;">
	<table>
		<thead>
		<tr>
			<th><input type="checkbox" name="selectAll" value="selectAll" form="batchStatusUpdateForm" id='selectAll'></th>
			<th>Application ID</th> <!-- 1 -->
			<th>Job Role</th><!-- 2 -->
			<th>Applicant Name</th><!-- 3 -->
			<th>Exp. Required</th><!-- 4 -->
			<th>Applicant Years of Exp.</th><!-- 5 -->
			<th>Credential 1</th><!-- 9 -->
			<th>Credential 2</th><!-- 10 -->
			<th>Credential 3</th><!-- 11 -->
			<th>Date of Application</th><!-- 12 -->
			<th>Status</th><!-- 13 -->
			<th></th><!-- 14 -->
			<th></th>
			<th></th>
		</tr>
		</thead>
		<tbody>
			<%
				if(apps != null){
				int counter = 0;
				for(JobApplication app : apps ){
					ApplicationStatus currentStatus = app.getStatus();
					boolean disableUpdateStatusButton = currentStatus == ApplicationStatus.JobSeekerAccepted 
							|| currentStatus == ApplicationStatus.JobSeekerDeclined 
							|| currentStatus == ApplicationStatus.JobConfirmed
							|| currentStatus == ApplicationStatus.JobClosed 
							|| currentStatus == ApplicationStatus.ApplicationRetracted;
					boolean disableConfirmJobButton = currentStatus != ApplicationStatus.JobSeekerAccepted;
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
				<td><input type="checkbox"
					class="selectBox" 
					name='checkbox<%=app.getId()%>' 
					id='checkbox<%=counter%>'
					value='checked' 
					form="batchStatusUpdateForm"
					<%=app.isChecked()?"checked":""%>
					<%=disableUpdateStatusButton?"disabled":"" %>
				></td>
				<td><%=app.getId()%></td><!-- 1 -->
				<td><%=app.getJobRole()%></td><!-- 2 -->
				<td><%=app.getJsFirstName() + " " + app.getJsLastName()%></td><!-- 3 -->
				<td><%=app.getYearsOfExp()%> years</td><!-- 4 -->
				<td><%=app.getJsYearsOfExp()%></td><!-- 5 -->
				<td><%=cred1%></td><!-- 9 -->
				<td><%=cred2%></td><!-- 10 -->
				<td><%=cred3%></td><!-- 11 -->
				<td><%=app.getApplicationDate()%></td><!-- 12 -->
				<td>
					<%
						if(disableUpdateStatusButton){
					%>
							<div><%=currentStatus.toString()%></div>
					<%	}
						else {
					%>	
					<select name="appStatus<%=app.getId()%>" id="<%=app.getId()%>" form="batchStatusUpdateForm"><!-- 13 -->
						<option value="<%=ApplicationStatus.Sent%>" <%=currentStatus == ApplicationStatus.Sent ? "selected" : ""%>>Sent</option>
						<option value="<%=ApplicationStatus.Received%>" <%=currentStatus == ApplicationStatus.Received ? "selected" : ""%>>Received</option>
						<option value="<%=ApplicationStatus.Processing%>" <%=currentStatus == ApplicationStatus.Processing ? "selected" : ""%>>Processing</option>
						<option value="<%=ApplicationStatus.PendingInterview%>" <%=currentStatus == ApplicationStatus.PendingInterview ? "selected" : ""%>>Pending Interview</option>
						<option value="<%=ApplicationStatus.EmployerAccepted%>" <%=currentStatus == ApplicationStatus.EmployerAccepted ? "selected" : ""%>>Employer Accepted</option>
						<option value="<%=ApplicationStatus.EmployerDeclined%>" <%=currentStatus == ApplicationStatus.EmployerDeclined ? "selected" : ""%>>Employer Declined</option>
					</select>
					<% }%>
				</td>
				<td>
					<button type="button" onclick="updateStatus('<%=app.getId()%>')" class="login-button update-status-button" <%=disableUpdateStatusButton?"disabled":"" %>>Update Status</button>
				</td><!-- 14 -->
				<td>
					<button type="button" onclick="confirmJob('<%=app.getId()%>')" class="login-button update-status-button" <%=disableConfirmJobButton?"disabled":"" %>>Confirm Job</button>
				</td>
				<td>
					<button type="button" onclick="viewSingleApplication('<%=app.getId()%>')" class="login-button update-status-button">View</button>
				</td>
			</tr>
			<% counter++; }
		}%>
		
		<%
			if(apps == null || apps.isEmpty()){
				%>
				<tr><td>No applications found.</td></tr>
		<%}%>
		</tbody>
	</table>
	</div>
	</form>
	<script>
		const updateSelectedAppsStatusTo = (status) => {
			const selectAllTag = document.getElementById("selectAll");
			let params = "";
			let counter = 0;
			let checkBoxTag = document.getElementById("checkbox"+counter);
			let appCount = 0;
			while(checkBoxTag != null){
				let appId = checkBoxTag.name.substring(8);
				console.log("pulling app id " + appId);
				if(checkBoxTag.checked && !checkBoxTag.disabled){
					params = params + "&app" + appCount + "=" + appId;	
					appCount++;
				}
				counter++;
				checkBoxTag = document.getElementById("checkbox"+counter);
			} 
			location.href = "/job-portal/EMUpdateSelectedStatusTo?appStatus="+status+params;
		}
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
		function viewSingleApplication(appID){
			location.href = "/job-portal/ViewSingleApplication?appID="+appID;
		}
		const sortByAppExpTag = document.getElementById("sortByAppExp");
		const sortByAppDateTag = document.getElementById("sortByAppDate");
		const sortByAppStatusTag = document.getElementById("sortByAppStatus");
		const sortByAppCredTag = document.getElementById("sortByAppCred");
		
		sortByAppExpTag.addEventListener("change", (event) => {
			console.log("detected sort option change: " + event.target.value);
			location.href = "/job-portal/EMSortApplications?sortOption="+event.target.value;
		});
		sortByAppDateTag.addEventListener("change", (event) => {
			console.log("detected sort option change: " + event.target.value);
			location.href = "/job-portal/EMSortApplications?sortOption="+event.target.value;
		});
		sortByAppStatusTag.addEventListener("change", (event) => {
			console.log("detected sort option change: " + event.target.value);
			location.href = "/job-portal/EMSortApplications?sortOption="+event.target.value;
		});
		sortByAppCredTag.addEventListener("change", (event) => {
			console.log("detected sort option change: " + event.target.value);
			location.href = "/job-portal/EMSortApplications?sortOption="+event.target.value;
		});
		
		const selectAllCheckbox = document.getElementById("selectAll");
		selectAllCheckbox.addEventListener("change", (event) => {
			if(event.target.checked){
				const selectboxes = document.getElementsByClassName("selectBox");
				for(let box of selectboxes){
					if(!box.disabled){
						box.checked = true;	
					}
				}
			}
			else {
				const selectboxes = document.getElementsByClassName("selectBox");
				for(let box of selectboxes){
					box.checked = false;
				}
			}
		});
		const selectboxes = document.getElementsByClassName("selectBox");
		for(let box of selectboxes){
			box.addEventListener("change", (event) => {
				if(!event.target.checked){
					selectAllCheckbox.checked = false;
				}
			});
		}
	</script>
	
</div>
<br><br><br><br><br>
