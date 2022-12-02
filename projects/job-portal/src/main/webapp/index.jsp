<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Land A Job - Home</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
<script src="https://kit.fontawesome.com/b5b7b3504f.js" crossorigin="anonymous"></script>
<link href="/job-portal/style.css" rel="stylesheet">
</head>
<body>
	<%@ page import="com.ncs.model.*"%>
	<%@ page import="java.util.*"%>
	<%
		@SuppressWarnings("unchecked")
		List<Job> searchResults = (ArrayList<Job>) session.getAttribute("searchResults");
	%>
	<div class="home-overall-container">
		<header class="nav-bar-header">
			<div class="site-logo">
				<a href="/job-portal/index.jsp" class="site-logo-link">Land A
					Job</a>
			</div>
			<nav class="nav-bar">
				<button onclick="login()" type="button" class="login-button">Login</button>
			</nav>
		</header>
		<div class="container-stack">
			<div class="body-base">
				<div class="home-background-hero"></div>
			</div>
			<div class="home-body-container">
				<div class="home-search-form-container">
					<form class="form-100-width border rounded-3 bg-light"
						action="/job-portal/MainSearchJobs?searchFrom=home"
						id="job-search-form">
						<div class="search-field-box">
							<input type="text" class="search-field" name="searchText"
								placeholder="Search jobs" id="search-entry">
							<button type="submit" class="search-button">
								<i class="fa-solid fa-magnifying-glass"></i>
							</button>
						</div>
						<div class="search-option-box">
							<div class="form-group">
								<label for="searchBy">Search by:</label>
							</div>
							<div class="form-group">
								<input type="radio" name="searchBy" value="byJobRole"
									id="searchByJobRole" checked> <label
									for="searchByJobRole">Job role</label>
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
							<input type="text" style="display: none" value="home"
								name="searchFrom">
						</div>
					</form>
				</div>
				<%
				if (searchResults != null) {
					int i = 0;
				%>
				<div class="search-results-box">
					<%
					for (Job j : searchResults) {
						String role = j.getRole();
						int salary = j.getSalary();
						int yearsOfExp = j.getYears_of_exp();
						String companyName = j.getCompany_name();
						i++;
						int imgCounter = (i % 4) + 1;
					%>
					<div class="job-item">
						<button type="button" class="job-item-button"
							onclick="goToLogin('<%=j.getId()%>')">
							<div class="logo-box">
								<div class="company-logo logo-<%=imgCounter%>"></div>
							</div>
							<div class="info-box">
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
							<div class="salary-box">
								<div class="job-salary job-meta">
									<%=salary%><br>SGD/year
								</div>
							</div>
						</button>
					</div>
					<%
					}
					%>
				</div>
				<%
				} else {
				%>
				<div class="banner-box">
					<div class="jobs-banner banner-1">
						<div class="banner-link-box">
							<a href="/job-portal/login.jsp?user=jobSeeker"
								class="job-banner-link"> Apply for jobs now! </a>
						</div>
					</div>
					<div class="jobs-banner banner-2">
						<div class="banner-link-box">
							<a href="/job-portal/login.jsp?user=jobSeeker"
								class="job-banner-link"> Pursue your dream job! </a>
						</div>

					</div>
					<div class="jobs-banner banner-3">
						<div class="banner-link-box">
							<a href="/job-portal/login.jsp?user=employer"
								class="job-banner-link"> Hire global talents easily! </a>
						</div>
					</div>
				</div>
				<%}%>
			</div>
		</div>
	</div>

	<br><br><br><br><br><br>
	<script src="validation.js" type="text/javascript"></script>
	<script>
		
		const login = () => {
			location.href = "/job-portal/login.jsp";
		}
		const goToLogin = (jobID) => {
			alert("Log in to view and apply");
			location.href="/job-portal/login.jsp?user=jobSeeker&jobIDStr="+jobID;
		}
	</script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
</body>
</html>