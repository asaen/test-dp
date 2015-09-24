<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>
    <c:set var="req" value="${pageContext.request}" />
    <c:set var="baseUrl" value="${req.scheme}://${req.serverName}:${req.serverPort}${req.contextPath}" />
	<div id="aside">
        Logged in as:
        ${pageContext.request.userPrincipal.name}
        <sec:authentication property="principal.authorities"/>
        (<a href="${baseUrl}/j_spring_security_logout">Logout</a>)
        <h1>Menu</h1>
		<ul>
			<li><a href="${baseUrl}">Home</a></li>
			<li><a href="${baseUrl}/users">List of users</a></li>
			<li><a href="${baseUrl}/users/add">Add user</a></li>
			<li><a href="${baseUrl}/items">List of items</a></li>
			<li><a href="${baseUrl}/items/add">Add item</a></li>
		</ul>
	</div>
</body>
</html>