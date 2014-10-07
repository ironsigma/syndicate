<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"
%><!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>Spring MVC</title>
		<link rel="stylesheet" href="<c:url value='/styles/default.css'/>">
	</head>
	<body>
		<h1>Spring MVC</h1>
		<h2>Name: <c:out value="${name}"/></h2>
		<h2>URL: <c:out value="${url}"/></h2>
	</body>
</html>