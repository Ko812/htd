<%@ page import="java.util.*"%>
<%@ page import="com.ncs.model.*"%>
<%
		String currentView = (String) request.getParameter("currentView");
		if(currentView == null){
			currentView = "view-jobs-applied";
		}
		List<Job> jobs;
		
		jobs = (ArrayList<Job>) session.getAttribute("searchResults");
		if(jobs == null){
			jobs = (List<Job>)session.getAttribute("all-jobs");
		}
%>
<div class="emp-main-content overflow-auto">
	<div class="js-search-form-container">
		<form class="form-max-width border rounded-3 bg-light"
			action="/job-portal/MainSearchJobs">
			<div class="js-search-field-box">
				<input type="text" class="search-field" name="searchText"
					placeholder="Search jobs">
				<button type="submit" class="search-button"><i class="fa-solid fa-magnifying-glass"></i></button>
			</div>
			<div class="search-option-box">
				<div class="form-group">
					<input type="radio" name="searchBy" value="byJobRole"
						id="searchByJobRole" checked> <label for="searchByJobRole">Job
						role</label>
				</div>
				<div class="form-group">
					<input type="radio" name="searchBy" value="byCompanyName"
						id="searchByCompanyName"> <label
						for="searchByCompanyName">Company Name</label>
				</div>
				<div class="form-group">
					<input type="radio" name="searchBy" value="byJobYearsOfExp"
						id="searchByYearsOfExperience"> <label
						for="searchByYearsOfExperience">Years of experience</label>
				</div>
				<div class="form-group">
					<input type="radio" name="searchBy" value="bySalary"
						id="searchBySalary"> <label for="searchBySalary">Salary</label>
				</div>
			</div>
			<input type="text" style="display:none" value="jobSeekerDashboard" name="searchFrom">
		</form>
	</div>
	<div class="js-search-results-box">
		<% 
		if(jobs != null){
			int i = 0;
			for(Job j : jobs){
				int jobID = j.getId();
				String role = j.getRole();
				int salary = j.getSalary();
				int yearsOfExp = j.getYears_of_exp();
				String companyName = j.getCompany_name();
				Date datePosted = j.getDatePosted();
				i++;
				int imgCounter = (i % 4)+ 1;
		%>
		<div class="search-page-job-item">
			<div class="js-job-item-button">
				<div class="js-logo-box">
					<div class="company-logo logo-<%=imgCounter%>"></div>
				</div>
				<div class="js-info-box">
					<div class="job-info-column">
						<div class="major-job-meta">
							<%=companyName%></div>
						<div class="minor-job-meta">
							<%=role%></div>
						<div class="minor-job-meta">
							At least
							<%=yearsOfExp%>
							years
						</div>
					</div>
				</div>
				<div class="js-salary-box">
					<span class="js-salary-line"><%=salary%></span>
					<span class="js-salary-line">SGD/year</span>
				</div>
				<div class="js-apply-box">
					<button type="button" class="job-apply-button"
						onclick="location.href='/job-portal/PrepareToApplyJob?jobID=<%=j.getId()%>'">View</button>
				</div>
			</div>
			<div class="js-job-item-row">
				<span>Date posted: <%=datePosted.toString()%></span>
			</div>
		</div>
		<%}}%>
	</div>
</div>
<br><br><br><br><br><br>