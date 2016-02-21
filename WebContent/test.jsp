<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="de.moaiosbeer.helper.* "%>


<html data-ng-app="myApp">
<title>MoaIosBeer</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="Sat, 01 Dec 2001 00:00:00 GMT">
<link href="favicon.ico" rel="icon" type="image/x-icon" />
<!--css-->
<link href="bower_components/bootstrap/dist/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<!--/css-->
<!--js-->
<script src="bower_components/jquery/dist/jquery.min.js"
	type="text/javascript"></script>
<script src="bower_components/angular/angular.min.js"
	type="text/javascript"></script>
<script src="bower_components/angular-route/angular-route.min.js"
	type="text/javascript"></script>
<script src="bower_components/bootstrap/dist/js/bootstrap.min.js"
	type="text/javascript"></script>
<script src="bower_components/angular/angularBasicAuth.js"
	type="text/javascript"></script>

<%
	// Globale Seitenvariabelen
	session = request.getSession(true);
	SessionOwner owner = (SessionOwner) session.getAttribute("sessionOwner");
	String username = owner.getName();
%>


</head>
<body data-ng-controller="MyController">
<%= owner %>
<br>
<br>
<br>
<%/*Diese JSP Dient allein zum debuggen der gamlist.
	Sie ist als sende test erstellt worden und überprüft die url parameter
*/ %>
	logoutflag: <%=request.getParameter("logoutflag") %>
	<br>
	mode: <%=request.getParameter("mode") %>
	<br>
	gameid: <%=request.getParameter("gameid") %>
	
</body>
</html>