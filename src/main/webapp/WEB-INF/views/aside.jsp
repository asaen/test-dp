<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>
	<div id="aside">
		<h1>Menu</h1>
        <c:set var="req" value="${pageContext.request}" />
        <c:set var="baseUrl" value="${req.scheme}://${req.serverName}:${req.serverPort}${req.contextPath}" />
		<ul>
			<li><a href="${baseUrl}">Home</a></li>
			<li><a href="${baseUrl}/users">List of users</a></li>
			<li><a href="${baseUrl}/users/add">Add user</a></li>
		</ul>
	</div>
</body>
</html>