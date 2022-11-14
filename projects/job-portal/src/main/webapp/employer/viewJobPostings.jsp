<%@ page import="java.util.*"%>
<%@ page import="com.ncs.model.*"%>
<% 
	@SuppressWarnings("unchecked")
	ArrayList<Job> jobs = (ArrayList<Job>)session.getAttribute("jobs");
	@SuppressWarnings("unchecked")
	ArrayList<Job> filteredPostings = (ArrayList<Job>)session.getAttribute("filtered-postings");
	String filterRole = (String) session.getAttribute("filterRole");
	String filterDatePosted = (String) session.getAttribute("filterDatePosted");
	String filterHasApplicant = (String) session.getAttribute("filterHasApplicant");
	if(filterRole == null || filterRole.equals("null")) {
		filterRole = "all";
	}
	if(filterDatePosted == null || filterDatePosted.equals("null")) {
		filterDatePosted = "all";
	}
	if(filterHasApplicant == null || filterHasApplicant.equals("null")) {
		filterHasApplicant = "false";
	}
	String searchField = (String) session.getAttribute("searchField");
	if(searchField == null){
		searchField = "";
	}
%>
<div class="emp-main-content applications-view">
	
	<div class="form-title">Job Postings</div>
	<div class="search-form-container">
		<form class="form-90-width border rounded-3 bg-light"
			action="/job-portal/ViewJobPostings">
			<div class="search-field-box">
				<input type="text" class="search-field" name="searchJobPosting" list="jobRolesDataList"
					placeholder="Search postings" value="<%=searchField%>">
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
				<button type="submit" class="search-button">&#128269</button>
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
					<input type="checkbox" name="filterByHasApplicants"
						value="onlyShowIfHasApplicants" id="filterByHasApplicants"
						<%=filterHasApplicant.equals("true") ? "checked" : ""%>> <label
						for="filterByHasApplicants" class="form-label-margin-left">Has
						Applicants</label>
				</div>
<!-- 				<div class="form-group filter-item layout-horizontal"> -->
<!-- 					<input type="button" value="Clear filters" id="clearFilters" -->
<!-- 						onclick="clearFilters()" style="width: 100px"> -->
<!-- 				</div> -->
			</div>
			<input type="text" name="viewBy" value="search" style="display:none; width:0px;height:0px">
		</form>
	</div>
<!-- 	<div class="em-search-form-container"> -->
<!-- 		<form action="" class="rounded-3 bg-light job-posting-search-form"> -->
			
<!-- 			<div class="job-posting-search-field-box"> -->
<!-- 				<label for="jobRoleSearch" class="form-label">Search: </label> -->
<!-- 				<input type="search" list="jobRolesDataList"  -->
<!-- 					class="search-field search-job-posting-field-width"  -->
<%-- 					name="searchJobPosting" placeholder="Search job postings" value="<%=searchField%>" --%>
<!-- 					id="jobRoleSearch"> -->
<!-- 				<button type="submit" class="search-button">&#128269;</button> -->
				
<!-- 			</div> -->
<!-- 			<div class="filter-options-box"> -->
			
<!-- 			</div> -->
<!-- 		</form> -->
<!-- 	</div> -->
	<table class="job-postings-table">
		<thead>
		<tr>
			<th>Job ID</th>
			<th>Date Posted</th>
			<th>Job Role</th>
			<th>Years of Experience</th>
			<th>Total Applicants</th>
			<th></th>
		</tr>
		</thead>
		<tbody>
			<%@ page import="java.util.*"%>
			<%@ page import="com.ncs.model.*"%>
			<%
				
				if(filteredPostings != null){
				for(Job job : filteredPostings ){
					
				%>
			<tr>
				<td><%=job.getId()%></td>
				<td><%=job.getDatePosted()%></td>
				<td><%=job.getRole()%></td>
				<td><%=job.getYears_of_exp()%></td>
				<td><%=job.getApplicants().size()%></td>
				<td>
					<button type="button"
						onclick="editJobPosting('<%=job.getId()%>')" class="login-button job-postings-button">Edit</button>
					<button type="button"
						onclick="viewApplicantsOfThisJob('<%=job.getId()%>')" class="login-button view-applicants-button">View applicants</button>
					<button type="button"
						onclick="deleteJobPosting('<%=job.getId()%>')" class="login-button job-postings-button">Delete</button>
				</td>
			</tr>
			<%}}%>
		</tbody>
	</table>
	<script>
		function viewApplicantsOfThisJob(job_id){
			location.href = "/job-portal/ViewJobApplications?jobID="+job_id;
		}
		function editJobPosting(job_id){
			location.href = "/job-portal/GetJobObject?jobID="+job_id;
		}
		function deleteJobPosting(job_id){
			if(confirm("Confirm delete job posting?")){
				location.href = "/job-portal/DeleteJobPosting?jobID="+job_id;	
			}
		}
		function filterJobs(role,datePosted,hasApplicant){
			location.href = "/job-portal/ViewJobPostings?viewBy=filter&filterRole="+role+"&filterDatePosted="+datePosted+"&filterHasApplicant="+hasApplicant;
		}
		const filterJobRoleTag = document.getElementById("filterByJobRole");
		const filterDatePostedTag = document.getElementById("filterByDatePosted");
		const filterHasApplicantsTag = document.getElementById("filterByHasApplicants");
		filterJobRoleTag.addEventListener("change", (event) => {
			let jobRoleFilterVal = event.target.value;
			let datePostedFilterVal = filterDatePostedTag.value;
			let hasApplicantsFilterVal = filterHasApplicantsTag.checked;
			filterJobs(jobRoleFilterVal,datePostedFilterVal ,hasApplicantsFilterVal);
		});
		
		filterDatePostedTag.addEventListener("change", (event) => {
			let jobRoleFilterVal = filterJobRoleTag.value;
			let datePostedFilterVal = event.target.value;
			let hasApplicantsFilterVal = filterHasApplicantsTag.checked;
			filterJobs(jobRoleFilterVal,datePostedFilterVal ,hasApplicantsFilterVal);
		});
		
		filterHasApplicantsTag.addEventListener("change", (event) => {
			let jobRoleFilterVal = filterJobRoleTag.value;
			let datePostedFilterVal = filterDatePostedTag.value;
			let hasApplicantsFilterVal = event.target.checked;
			filterJobs(jobRoleFilterVal,datePostedFilterVal ,hasApplicantsFilterVal);
		});
		console.log("ran to clear filters definition");
		const clearFilters = ()=>{
			filterJobs("all","all","false");
		}
	</script>
</div>