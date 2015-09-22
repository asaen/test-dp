<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="<c:url value="/resources/css/screen.css" />" rel="stylesheet" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Users</title>
</head>
<body>
	<jsp:include page="../aside.jsp" />
	<div id="content">
		<h1>List of users</h1>
		<table class="simpletablestyle">
			<thead>
				<tr>
					<th width="50">ID</th>
					<th width="300">Email</th>
					<th width="200">Actions</th>
				</tr>
			</thead>
			<tbody>
                <c:set var="req" value="${pageContext.request}" />
                <c:set var="baseUrl" value="${req.scheme}://${req.serverName}:${req.serverPort}${req.contextPath}" />
				<c:forEach items="${users}" var="u">
					<c:url var="show" value="${baseUrl}/users/show?id=${u.id}" />
					<c:url var="update" value="${baseUrl}/users/update?id=${u.id}" />
					<c:url var="delete" value="${baseUrl}/users/delete?id=${u.id}" />
					<tr>
						<td><c:out value="${u.id}" /></td>
						<td><c:out value="${u.email}" /></td>
						<td>
							<a href="${show}">Show</a>
							<a href="${update}">Update</a>
							<a href="${delete}">Delete</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>