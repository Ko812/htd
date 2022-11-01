<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Car Service System - Buy Car Accessories</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
<link href="../style.css" rel="stylesheet">
</head>
<body>
	<div class="container-stack">
		<div class="background-hero"></div>
		<div class="body-container">
			<form class="p-4 p-md-5 border rounded-3 bg-light" action="/CarServiceSystem/CheckoutItems">
			<div class="form-title">Buy Accessories</div>
			<table>
				<tr>
					<th>SN</th>
					<th>Item</th>
					<th>Price</th>
					<th>Quantity</th>
				</tr>
				<tr>
					<td>1</td>
					<td>Car Decal</td>
					<td>$100</td>
					<td><input type="number" name="decalQuantity" value=0 min=0 class="form-control"></td>
				</tr>
				<tr>
					<td>2</td>
					<td>Car Hooks</td>
					<td>$15</td>
					<td><input type="number" name="carHookQuantity" value=0 min=0 class="form-control"></td>
				</tr>
				<tr>
					<td>3</td>
					<td>Hand Phone Holder</td>
					<td>$35</td>
					<td><input type="number" name="handphoneHolderQuantity" value=0 min=0 class="form-control"></td>
				</tr>
				<tr>
					<td>4</td>
					<td>Air Freshener (x2)</td>
					<td>$7</td>
					<td><input type="number" name="airRefreshenerQuantity" value=0 min=0 class="form-control"></td>
				</tr>
				<tr>
					<td>5</td>
					<td>Front & Rear Camera Pack</td>
					<td>$250</td>
					<td><input type="number" name="frontRearCameraQuantity" value=0 min=0 class="form-control"></td>
				</tr>
			</table>
			<%
				String formMessage = (String) session.getAttribute("buy-accessories-form-message");
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
						<input type="submit" value="Check out" class="btn btn-primary">
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