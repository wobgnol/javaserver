<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="de.moaiosbeer.helper.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%SessionOwner owner = new SessionOwner(); 
owner.setGameID(1);
owner.setOwnerID(2);
owner.setSessionID("42");
owner.setUserAktion(RequestConstants.MODE_TURN);
request.getSession().setAttribute(RequestConstants.ATTR_ORDER, "5");

session.setAttribute(RequestConstants.ATTR_SESSION_OWNER, owner);
%>
<form action="./game" method="Post">
<input type="Text" name="TTI"/>
<button type="submit">Ein Button</button>
</form>
<%= response.getStatus() %>
</body>
</html>