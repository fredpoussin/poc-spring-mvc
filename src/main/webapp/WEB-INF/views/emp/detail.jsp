<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Employees</title>
</head>
<body>
<table border=1 widht="50%">
	<tr>
		<td>ID</td>
		<td>${employee.id}</td>
	</tr>
	<tr>
		<td>Name</td>
		<td>${employee.name}</td>
	</tr>
	<tr>
		<td>Email</td>
		<td>${employee.email}</td>
	</tr>
</table>
</body>
</html>