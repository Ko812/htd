<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
<link href="../style.css" rel="stylesheet">
</head>
<body>
	<div class="container-stack">
		<div class="background-hero"></div>
		<div class="body-container">
			<div class="form-title">Service Status</div>
			<table >
			<tr>
				<th>SN</th>
				<th>Car Registration Number</th>
				<th>Service Request</th>
				<th>Service Status</th>
			</tr>
			<tbody>
				<%@ page import="java.util.*"%>
				<%@ page import="com.ncs.model.*"%>
				<%	
				ArrayList<CarModel> cars = (ArrayList<CarModel>)session.getAttribute("cars");
				int i = 1;
				for(CarModel car : cars ){
					if(!car.getServiceStatus().equals("false")){
						out.print("<tr>");
						out.print("<td>" + i +"</td>");
						out.print("<td>" + car.getCarRegistrationNumber() +"</td>");
						out.print("<td>" + car.getServiceRequest() +"</td>");
						out.print("<td>" + car.getServiceStatus() +"</td>");
						out.println("</tr>");
						i++;
					}
				}
				%>
			</tbody>
		</table>
		<div class="row">
			<div class="row-item">
				<button type="button" onclick="back()" class="btn btn-primary">Return</button>
			</div>
		</div>	
		</div>
	</div>
	<script src="form.js"></script>
	<!-- JavaScript Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
</body>
</html>