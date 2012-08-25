<%@page import="waps.hw.vibhas.Utilities"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login MiniBlog</title>
</head>
<body>
<%@ include file="header.html" %>
<form action="loginServlet" method="post">
<label>Username*: <input name="userName" type="text" id="userName" value="${param.userName}"></label>
<span class="error">${errors.userName}</span><br>
<label>Password*: <input name="password" type="password" id="password" value=""></label>
<span class="error">${errors.password}</span><br>
<span class="error">${errors.loginError}</span><br>
<input name="loginButton" type="submit" value="Login">
</form>
Not Registered? <a href="register.jsp">Register on MiniBlog</a>
<%	
if (Utilities.checkSessionForUser(session)) 
{
	response.sendRedirect("home");
}
else
{
	String errorMessage = (String) request.getAttribute("DbError");
 	if(errorMessage!=null)
 	{
 		out.print("<script> alert(\"" + errorMessage + "\") </script>");
 		request.removeAttribute("DbError");
 	} 
}
 %>
</body>
</html>