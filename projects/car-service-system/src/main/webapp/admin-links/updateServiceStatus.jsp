<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Car Service Admin - Update Service Status</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
<link href="../style.css" rel="stylesheet">
</head>

<body>
	<header class="header-container">
		<div class="admin-header-hero-img-box">
			<img src="/CarServiceSystem/assets/admin-icon-2.jpg" class="admin-hero-img">
		</div>
	</header>
	<div class="body-container">
		<form class="p-4 p-md-5 border rounded-3 bg-light" action="/CarServiceSystem/UpdateServiceStatus">
		<div class="form-title">Update Service Status</div>
		<table >
			<tr>
				<th>User name</th>
				<th>Car Registration Number</th>
				<th>Service Request</th>
				<th>Service Status</th>
			</tr>
			<tbody>
				<%@ page import="java.util.*"%>
				<%@ page import="com.ncs.model.*"%>
				<tr>
					<td><%=session.getAttribute("customerUsername")%></td>
					<td><%=session.getAttribute("customerCarRegNum")%></td>
					<td><%=session.getAttribute("customerServiceRequest")%></td>
					<td>
						<input type="text" name="serviceStatus" value="<%=session.getAttribute("customerServiceStatus")%>">
					</td>
				</tr>
				<tr>
					<td>
						<input type="submit" value="Update">
					</td>
				</tr>
			</tbody>
		</table>
		</form>
		<br>
		<div class="row">
			<div class="row-item">
				<button type="button" onclick="back()">Return</button>
			</div>
		</div>	
	</div>
	<script src="form.js"></script>
	<!-- JavaScript Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
	
</body>
</html>