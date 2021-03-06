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
<title>Add new item</title>
</head>
<body>
    <jsp:include page="../aside.jsp" />
    <div id="content">
    	<h1>Add new item</h1>
    	<c:url var="save" value="add" />
    	<form:form modelAttribute="item" method="POST" action="${save}">
    		<!-- ID -->
    		<form:label path="id">ID</form:label>
    		<form:input path="id" disabled="true" />
    		<br>
    		<!-- Title -->
    		<form:label path="title">Title</form:label>
    		<form:input path="title" />
    		<br>
    		<input type="submit" value="Add" />
    	</form:form>
    </div>
</body>
</html>