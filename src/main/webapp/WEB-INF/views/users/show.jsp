<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="<c:url value="/resources/css/screen.css" />" rel="stylesheet" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>User details</title>
</head>
<body>
    <jsp:include page="../aside.jsp" />
    <div id="content">
    	<h1>User details</h1>
    	<table class="simpletablestyle">
    		<thead>
    			<tr>
    				<th width="50">ID</th>
    				<th width="300">Email</th>
    				<th width="200">Password</th>
    			</tr>
    		</thead>
    		<tbody>
    			<tr>
    				<td><c:out value="${user.id}" /></td>
    				<td><c:out value="${user.email}" /></td>
    				<td><c:out value="${user.password}" /></td>
    			</tr>
    		</tbody>
    	</table>
    </div>
</body>
</html>