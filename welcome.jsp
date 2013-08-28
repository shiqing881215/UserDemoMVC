<html>
<head><title>Welcome page</title></head>

<body>
<h1 style="color: red;"> Welcome <%= request.getParameter("userName") %></h1>

Click <a href="logout.do">here</a> to logout.

</body>
</html>