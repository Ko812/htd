<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Car Service System - Checkout</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
<link href="../style.css" rel="stylesheet">
</head>
<body>
	<script>
		function reload (itemName){
			location.href = "/CarServiceSystem/car-links/checkoutItems.jsp?remove="+itemName;
		}
	</script>
	<div class="container-stack">
		<div class="background-hero"></div>
		<div class="body-container">
			<form class="p-4 p-md-5 border rounded-3 bg-light" action="/CarServiceSystem/BuyAccessories">
			<div class="form-title">Check-out Items</div>
			<table>
				<tr>
					<th>SN</th>
					<th>Item</th>
					<th>Price</th>
					<th>Quantity</th>
					<th>Check</th>
				</tr>
				<%@ page import="java.util.*"%>
				<%@ page import="com.ncs.model.*"%>
				<%
				ArrayList<CarAccessory> items = (ArrayList<CarAccessory>)session.getAttribute("items");
				String removeItem = request.getParameter("remove");
				if(removeItem != null){
					for(CarAccessory item : items){
						if(item.getName().equals(removeItem)){
							item.setChecked();
						}
					}
					session.setAttribute("items", items);
				}
				int i = 1;
				for(CarAccessory item : items ){
					if(item.getChecked()){
				%>
					<tr>
						<td><%=i%></td>
						<td><%=item.getName()%></td>
						<td>$<%=item.getPrice()%></td>
						<td><%=item.getQuantity()%></td>
						<td><button type="button" onclick="reload('<%=item.getName()%>')" class="btn btn-primary">Remove</button></td>
					</tr>
				<% i++;}}
				%>
				<tr>
					<td colspan="4" class="form-sub-total-label">Sub-Total:</td>
					<td colspan="1" class="form-sub-total">$<%=CarAccessory.getTotal(items)%></td>
				</tr>
			</table>
			<%
				String formMessage = (String) session.getAttribute("check-out-form-message");
				if(formMessage != null){%>
					<div class="form-message">
						<%=formMessage%>
					</div>	
			<%}%>
				<br><br><br><br><br>
				<div class="row">
					<div class="row-item">
						<button type="button" onclick="back()" class="btn btn-primary">Return</button>
					</div>
					<div class="row-item">
						<input type="submit" value="Send order" class="btn btn-primary">
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