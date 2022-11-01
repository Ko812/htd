<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Car Service Admin - Pending Service Requests</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
<link href="../style.css" rel="stylesheet">
</head>
<body>
	<script>
		function updateStatus (uid, cr, st, sr){
			location.href = "/CarServiceSystem/RedirectServiceStatus?customerID="+uid+"&regnum="+cr+"&status="+st+"&request="+sr;
		}
	</script>
	<header class="header-container">
		<div class="admin-header-hero-img-box">
			<img src="/CarServiceSystem/assets/admin-icon-2.jpg" class="admin-hero-img">
		</div>
	</header>
	<div class="body-container">
		<div class="form-title">View Pending Service Requests</div>
		<table >
			<tr>
				<th>Customer User Name</th>
				<th>Car Model</th>
				<th>Car Type</th>
				<th>Car Registration Number</th>
				<th>Service Request</th>
				<th>Service Status</th>
				<th></th>
			</tr>
			<tbody>
				<%@ page import="java.util.*"%>
				<%@ page import="com.ncs.model.*"%>
				<%
				@SuppressWarnings("unchecked")
				ArrayList<CarModel> requests = (ArrayList<CarModel>)session.getAttribute("serviceRequests");
				for(CarModel req : requests ){
				%>
					<tr>
						<td><%=req.getUsername()%></td>
						<td><%=req.getCarModel()%></td>
						<td><%=req.getCarType()%></td>
						<td><%=req.getCarRegistrationNumber()%></td>
						<td><%=req.getServiceRequest()%></td>
						<td><%=req.getServiceStatus()%></td>
						<td>
							<button type="button" onclick="updateStatus('<%=req.getUserId()%>','<%=req.getCarRegistrationNumber()%>','<%=req.getServiceStatus()%>','<%=req.getServiceRequest()%>')">Update</button>
						</td>
					</tr>
				<%}%>
			</tbody>
		</table>
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