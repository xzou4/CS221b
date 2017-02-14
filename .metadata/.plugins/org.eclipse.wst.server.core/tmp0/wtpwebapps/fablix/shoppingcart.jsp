<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Shopping Cart</title>
</head>
<body>

<table cellpadding="2" cellspacing="2" border="1">
	<tr>
		<th>ID</th>
		<th>Name</th>
		<th>Price</th>
		<th>Quantity</th>
		<th>Sub Total</th>
	</tr>
	<c:forEach var="it" items="${sessionScope.cart }">
		<tr>
			<td align="center">Delete</td>
			<td>${it.p.id }</td>
			<td>${it.p.id }</td>
			<td>${it.p.price }</td>
			<td>${it.quantity }</td>
			<td>${it.p.price * it.quantity}</td>
		</tr>
	</c:forEach>
</table>

</body>
</html>