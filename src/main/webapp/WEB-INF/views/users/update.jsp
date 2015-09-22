<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="<c:url value="/resources/css/screen.css" />"
	rel="stylesheet" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Update user details</title>
</head>
<body>
	<h1>Update user details</h1>
	<c:url var="save" value="update?id=${user.id}" />
	<form:form modelAttribute="user" method="POST" action="${save}">
		<!-- ID -->
		<form:label path="id">ID</form:label>
		<form:input path="id" disabled="true" />
		<br>
		<!-- Email -->
		<form:label path="email">Email</form:label>
		<form:input path="email" />
		<br>
		<!-- Password -->
		<form:label path="password">Password</form:label>
		<form:password path="password" />
		<br>
		<input type="submit" value="Save" />
	</form:form>
</body>
</html>