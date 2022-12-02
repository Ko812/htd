<%@ page import="java.util.*"%>
<%@ page import="com.ncs.model.*"%>
<% 
	@SuppressWarnings("unchecked")
	ArrayList<JobApplication> apps = (ArrayList<JobApplication>)session.getAttribute("applications");
%>
<div class="emp-main-content applications-view">
	<script>
		const acceptOffer = (appID)=>{
			location.href = "/job-portal/AcceptOffer?appID="+appID;
		}
		const cancelApplication = (appID)=>{
			location.href = "/job-portal/JSDeleteApplication?appID="+appID;
		}
		const editApplication= (appID)=>{
			location.href = "/job-portal/PrepareEditJobApplication?appID="+appID;
		}
	</script>
	<div class="form-title">View Jobs Applications Status</div>
	<table>
		<tr>
			<th>Application ID</th><!-- 1 -->
			<th>Company Name</th><!-- 2 -->
			<th>Company Email</th><!-- 3 -->
			<th>Job Role</th><!-- 4 -->
			<th>Job Desc</th><!-- 5 -->
			<th>Annual<br>Salary<br>(SGD)</th><!-- 6 -->
			<th>Years Of Experience Required</th><!-- 7 -->
			<th>Date of Application</th><!-- 8 -->
			<th>Application Status</th><!-- 9 -->
			<th></th><!-- 10 -->
		</tr>
		<tbody>
			<%
				if(apps != null){	
				for(JobApplication app : apps ){
					int appID = app.getId();//1
					String companyName = app.getCompanyName();//2
					String companyEmail = app.getCompanyEmail();//3
					String jobRole = app.getJobRole();//4
					String jobDesc = app.getJobDesc();//5
					int salary = app.getSalary();//6
					int yearsOfExp = app.getYearsOfExp();//7
					Date appDate = app.getApplicationDate();//8
					ApplicationStatus appStatus = app.getStatus();//9
					boolean disableEditButton = appStatus == ApplicationStatus.JobConfirmed 
					|| appStatus == ApplicationStatus.EmployerAccepted 
					|| appStatus == ApplicationStatus.EmployerDeclined 
					|| appStatus == ApplicationStatus.JobSeekerAccepted
					|| appStatus == ApplicationStatus.JobSeekerDeclined
					|| appStatus == ApplicationStatus.PendingInterview
					|| appStatus == ApplicationStatus.ApplicationRetracted
					|| appStatus == ApplicationStatus.JobClosed;
				%>
			<tr>
				<td><%=appID%></td><!--1-->
				<td><%=companyName%></td><!--2-->
				<td><%=companyEmail%></td><!--3-->
				<td><%=jobRole%></td><!--4-->
				<td><%=jobDesc%></td><!--5-->
				<td><%=salary%></td><!--6-->
				<td><%=yearsOfExp%></td><!--7-->
				<td><%=appDate%></td><!--8-->
				<td style="font-weight:bold;"><%=appStatus%></td><!--9-->
				<td><!--10-->
					<div class="align-column-center">
					<button type="button" onclick="editApplication('<%=app.getId()%>')"  class="login-button job-application-button" <%=disableEditButton?"disabled":"" %>>Edit</button>
					<button type="button"
						onclick="acceptOffer('<%=app.getId()%>')" <%=appStatus==ApplicationStatus.EmployerAccepted?"":"disabled"%> class="login-button job-application-button">Accept offer</button>
					<button type="button" onclick="cancelApplication('<%=app.getId()%>')" class="login-button job-application-button" <%=disableEditButton?"disabled":"" %>>Cancel</button>
					</div>
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
	<br><br><br><br><br><br>
</div>
