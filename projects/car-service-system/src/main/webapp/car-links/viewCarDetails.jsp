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
	<script>
		function editCar (reg_num){
			location.href = "/CarServiceSystem/EditCarDetails?carRegistrationNumber="+reg_num;
		}
		function deleteCar (reg_num){
			location.href = "/CarServiceSystem/DeleteCar?carRegistrationNumber="+reg_num;
		}
	</script>
	<div class="container-stack">
		<div class="background-hero"></div>
		<div class="body-container">
			<div class="form-title">View Car Details</div>
			<table >
			<tr>
				<th>Car Model</th>
				<th>Car Type</th>
				<th>Car Registration Number</th>
				<th></th>
			</tr>
			<tbody>
				<%@ page import="java.util.*"%>
				<%@ page import="com.ncs.model.*"%>
				<%
				@SuppressWarnings("unchecked")
				ArrayList<CarDetails> cars = (ArrayList<CarDetails>)session.getAttribute("carDetails");
				for(CarDetails car : cars ){
				%>
					<tr>
						<td><%=car.getModel()%></td>
						<td><%=car.getType()%></td>
						<td><%=car.getRegistrationNumber()%></td>
						<td>
							<button type="button" onclick="editCar('<%=car.getRegistrationNumber()%>')">Edit</button>
							<button type="button" onclick="deleteCar('<%=car.getRegistrationNumber()%>')">Delete Car</button>
						</td>
					</tr>
				<%}%>
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