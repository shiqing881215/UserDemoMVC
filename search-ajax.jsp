<?xml version="1.0" encoding="utf-8"?>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!--This is the jsp file used to hold result back from the server -->

<% response.setHeader("Content-Type","text/xml"); %>

<results>
<c:forEach var="name" items="${namelist}" > 
	<result>
		
		<username><c:out value="${name.userName}"/></username>
	</result>
</c:forEach>
</results>