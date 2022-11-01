<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Car Service Admin - View All Customers</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
<link href="../style.css" rel="stylesheet">
</head>
<body>
	<script>
		function editCustomer (un){
			console.log(un);
			location.href = "/CarServiceSystem/EditCustomerDetails?username="+un;
		}
		function deleteCustomer (un){
			location.href = "/CarServiceSystem/DeleteCustomer?username="+un;
		}
	</script>
	<header class="header-container">
		<div class="admin-header-hero-img-box">
			<img src="/CarServiceSystem/assets/admin-icon-2.jpg" class="admin-hero-img">
		</div>
	</header>
	<div class="body-container">
		<div class="form-title">View All Customers</div>
		<table >
		<tr>
			<th>Customer ID</th>
			<th>Name</th>
			<th>User name</th>
			<th>Email</th>
			<th></th>
		</tr>
		<tbody>
			<%@ page import="java.util.*"%>
			<%@ page import="com.ncs.model.*"%>
			<%
			@SuppressWarnings("unchecked")
			ArrayList<Model> allCustomers = (ArrayList<Model>)session.getAttribute("allCustomers");
			for(Model customer : allCustomers ){
			%>
				<tr>
					<td><%=customer.customer_id%></td>
					<td><%=customer.getName()%></td>
					<td><%=customer.getUsername()%></td>
					<td><%=customer.getEmail()%></td>
					<td>
						<button type="button" onclick="editCustomer('<%=customer.getUsername()%>')">Edit</button>
						<button type="button" onclick="deleteCustomer('<%=customer.getUsername()%>')">Delete</button>
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