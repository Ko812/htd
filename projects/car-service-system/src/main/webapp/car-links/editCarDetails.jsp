<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Car Service System - Edit Car Details</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
<link href="../style.css" rel="stylesheet">
</head>
<body>
	<div class="container-stack">
		<div class="background-hero"></div>
		<div class="body-container">
			<form class="p-4 p-md-5 border rounded-3 bg-light" action="/CarServiceSystem/UpdateCarDetails">
				<div class="form-title">Edit Car Details</div>
				<div class="form-floating mb-3">
					<input type="text" name="carModel" class="form-control" placeholder="Car model" id="floatingCarModel" value="<%=(String) session.getAttribute("car_model")%>">
					<label for="floatingCarModel">Car model</label>
				</div>
				<div class="form-floating mb-3">
					<input type="text" name="carType" class="form-control" placeholder="Car type" id="floatingCarType" value="<%=(String) session.getAttribute("car_type")%>">
					<label for="floatingCarType">Car type</label>
				</div>
				<div class="form-floating mb-3">
					<input type="text" name="carRegistrationNumber" class="form-control" placeholder="Car registration number" id="floatingRegNum" value="<%=(String) session.getAttribute("reg_num")%>">
					<label for="floatingRegNum">Car registration number</label>
				</div>
				<%
					String formMessage = (String) session.getAttribute("edit-car-form-message");
					if(formMessage != null){%>
						<div class="form-message">
							<%=formMessage%>
						</div>	
				<%}%>
				<div class="row">
					<div class="row-item">
						<button type="button" onclick="back()" class="btn btn-primary">Return</button>
					</div>
					<div class="row-item">
						<input type="submit" value="Edit" class="btn btn-primary">
					</div>
				</div>
			</form>
		</div>
	</div>
	<script src="form.js"></script>
	<!-- JavaScript Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
</body>
</html>