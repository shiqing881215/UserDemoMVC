<%@page import="java.util.List" %>
<html>
<head><title>Login Page</title></head>
<body>
	<h1>Login</h1>
	
	<%
		List<String> errors = (List<String>)request.getAttribute("errors");
		for (String error : errors) {
	%>
		<h3 style="color: red;"> <%=error %> </h3> 
		<br/>
	<% } %>
		
	<!-- This decide when click login and register it still sends the request to login servlet -->
	<form action="login.do" method="POST">
		<table>
			<tr>
				<td style="font: large;"> Name </td>
				<td>
					<input type="text" name="userName" value="${loginForm.userName}" />
				</td>
			</tr>
			<tr>
				<td style="font: large;"> Password </td>
				<td>
					<input type="password" name="password" />
				</td>
			</tr>
			<tr>
				<td>
					<input type="submit" name="action" value="Login" />
					<input type="submit" name="action" value="Register" />
				</td>
			</tr>
		</table>
	</form>

</body>
</html>