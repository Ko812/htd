<%@ page import="java.util.*"%>
<%@ page import="com.ncs.model.*"%>
<% 
	@SuppressWarnings("unchecked")
	JobApplication app = (JobApplication)session.getAttribute("app-to-view");
	ApplicationStatus currentStatus = app.getStatus();
	boolean disableUpdateStatusButton = currentStatus == ApplicationStatus.JobSeekerAccepted 
			|| currentStatus == ApplicationStatus.JobSeekerDeclined 
			|| currentStatus == ApplicationStatus.JobConfirmed
			|| currentStatus == ApplicationStatus.JobClosed
			|| currentStatus == ApplicationStatus.ApplicationRetracted;
	boolean disableConfirmJobButton = currentStatus != ApplicationStatus.JobSeekerAccepted;
%>
<div class="emp-main-content">
	<div class="form-title"><%=app.getJobRole()%></div>
	<table  style="width:800px">
		<thead>
		<tr>
			<th>Experience Required</th><!-- 4 -->
			<th>Job Description</th><!-- 5 -->
			<th>Salary Offer</th><!-- 6 -->
			<th>Application Date</th><!-- 7 -->
		</tr>
		</thead>
		<tbody>
			<tr>
				<td style="width:20%"><%=app.getYearsOfExp()%> years</td><!-- 4 -->
				<td style="width:50%"><%=app.getJobDesc()%></td><!-- 11 -->
				<td style="width:10%"><%=app.getSalary()%></td>
				<td style="width:20%"><%=app.getApplicationDate()%></td><!-- 12 -->
			</tr>
		</tbody>
	</table>
	
	<div class="form-title">Applicant's Name: <%=app.getJsFirstName()%> <%=app.getJsLastName()%></div>
	<div class="applicant-details-box">
		<div class="applicant-personal-details-box">
			<div class="applicant-detail-header">Personal Details</div>
			<div class="applicant-personal-details-item">
				<label for="applicationID" class="applicant-detail-label">Application ID</label>
				<div class="applicant-detail-value" id="applicationID"><%=app.getId()%></div>
			</div>
			<div class="applicant-personal-details-item">
				<label for="applicantContact" class="applicant-detail-label">Applicant's Contact</label>
				<div class="applicant-detail-value" id="applicantContact"><%=app.getJsContact()%></div>
			</div>
			<div class="applicant-personal-details-item">
				<label for="applicantEmail" class="applicant-detail-label">Applicant's Email</label>
				<div class="applicant-detail-value" id="applicantEmail"><%=app.getJsEmail()%></div>
			</div>
			<div class="applicant-personal-details-item">
				<label for="applicantIDNo" class="applicant-detail-label">Applicant's Identity No.</label>
				<div class="applicant-detail-value" id="applicantIDNo"><%=app.getJsIdentificationNumber()%></div>
			</div>
		</div>
		<br><br>
		<div class="applicant-credentials-box">
			<div class="applicant-detail-header">Applicant's Credentials</div>
			<div class="applicant-personal-details-item">
				<label for="applicantExp" class="applicant-detail-label">Applicant's Experience</label>
				<div class="applicant-detail-value" id="applicantExp"><%=app.getJsYearsOfExp()%></div>
			</div>
			<%
				List<String> allCreds = app.getApplicationCredential();
				for(int i=0;i<allCreds.size();i++){
			%>
			<div class="applicant-personal-details-item">
				<label for="applicantCred-<%=i%>" class="applicant-detail-label">Applicant's Credential <%=i+1%></label>
				<div class="applicant-detail-value" id="applicantCred-<%=i%>"><%=allCreds.get(i)%></div>
			</div>
			
			<%}%>
			
		</div>
		<br><br><br><br><br>
		<div class="application-outcome-row">
			<label for="applicationOutcome" class="applicant-detail-label">Application Status</label>
			<div class="application-outcome-item">
			<%
				if(disableUpdateStatusButton){
			%>
				<div><%=currentStatus.toString()%></div>
			<%	}
				else {
			%>	
				<select name="appStatus<%=app.getId()%>" id="<%=app.getId()%>"><!-- 13 -->
					<option value="<%=ApplicationStatus.Sent%>" <%=currentStatus == ApplicationStatus.Sent ? "selected" : ""%>>Sent</option>
					<option value="<%=ApplicationStatus.Received%>" <%=currentStatus == ApplicationStatus.Received ? "selected" : ""%>>Received</option>
					<option value="<%=ApplicationStatus.Processing%>" <%=currentStatus == ApplicationStatus.Processing ? "selected" : ""%>>Processing</option>
					<option value="<%=ApplicationStatus.PendingInterview%>" <%=currentStatus == ApplicationStatus.PendingInterview ? "selected" : ""%>>Pending Interview</option>
					<option value="<%=ApplicationStatus.EmployerAccepted%>" <%=currentStatus == ApplicationStatus.EmployerAccepted ? "selected" : ""%>>Employer Accepted</option>
					<option value="<%=ApplicationStatus.EmployerDeclined%>" <%=currentStatus == ApplicationStatus.EmployerDeclined ? "selected" : ""%>>Employer Declined</option>
				</select>
			<%}%>
			</div>
		</div>
		<div class="application-outcome-row">
			<div class="application-outcome-item">
				<button type="button" onclick="updateStatus('<%=app.getId()%>')" class="login-button update-status-button" <%=disableUpdateStatusButton?"disabled":"" %>>Update Status</button>
			</div>
			<div>
				<button type="button" onclick="confirmJob('<%=app.getId()%>')" class="login-button update-status-button" <%=disableConfirmJobButton?"disabled":"" %>>Confirm Job</button>
			</div>
		</div>
	</div>
	
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
