<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Shopping Cart</title>
<script>
function update(item)
{
	item.setQuantity(this.optins[this.selectedIndex].text);
	}
</script>
</head>
<body>


<a href="javascript:history.back()">Continue Shopping</a>
<form action="shoppingCartController?action=update" method="post">
<table cellpadding="2" cellspacing="2" border="1">
	<tr>
		<th>Option</th>
		<th>ID</th>
		<th>Name</th>
		<th>Price</th>
		<th>Quantity <input type="submit" value="update"></th>
		<th>Sub Total</th>
	</tr>
	<c:set var="s" value="0"></c:set>
	<c:forEach var="it" items="${sessionScope.cart }">
		<c:set var="s" value="${s + it.price * it.quantity }"></c:set>
		<tr>
			<td align="center"><a href="shoppingCartController?id=${it.id }&action=delete">Delete</td>
			<td>${it.id }</td>
			<td><a href='titlelink?param=${it.name}'>${it.name }</a></td>
			<td>${it.price }</td>
			<td><input type="text" name="quantity" value="${it.quantity }"
			style="width: 60px;">
			
			<!-- <form action="shoppingCartController" >
			<select name="quantity" onchange="update(this, it)">
			<option value = "1">1</option>
			<option value = "2">2</option>
			<option value = "3">3</option>
			<option value = "4">4</option>
			<option value = "5">5</option>
			<option value = "6">6</option>
			<option value = "7">7</option>
			<option value = "8">8</option>
			<option value = "9">9</option>
			<option value = "10">10</option>
			</select>
			</form>
			 -->
			 
			 </td>
			<td>${it.price * it.quantity}</td>
		</tr>
	</c:forEach>
	<tr>
		<td colspan="5" align="right">Sum</td>
		<td>${s }</td>
	</tr>
</table>
</form>
<a href="checkout.html?sum=${s }">Checkout</a>
</body>
</html>