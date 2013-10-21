<html>
<head>
<title>Welcome page</title>
<script type="text/javascript" src="ajax.js"></script>
<script type="text/javascript" src="search-suggest.js"></script>

</head>

<body>




<h1 style="color: red;"> Welcome <%= request.getParameter("userName") %></h1>
<br/>

<h4>Search demo for XMLHttpRequest, try to enter a user name.</h4>
<input type="text" id="username" name="username" onkeyup="searchSuggest(this);" autocomplete="off"/>
<div id="search_suggest"></div>
<br/>

Click <a href="logout.do">here</a> to logout.

</body>
</html>