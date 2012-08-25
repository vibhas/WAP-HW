<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Register on MiniBlog</title>
</head>
<body>
<%@ include file="header.html" %>
<form action="registerServlet" method="post">
<table>
  <tr>
    <td>Required Username*:</td>
    <td><input name="reqUserName" type="text" id="reqUserName" value="${param.reqUserName}">
      <span class="error">${errors.reqUserName}</span></td>
  </tr>
  <tr>
    <td>Name*:</td>
    <td><label>
      <input name="name" type="text" id="name" value="${param.name}">
    </label>
      <span class="error">${errors.name}</span></td>
  </tr>
  <tr>
    <td>Password*:</td>
    <td><label>
      <input name="password1" type="password" id="password1" value="">
    </label>
      <span class="error">${errors.password1}</span></td>
  </tr>
  <tr>
    <td>Confirm Password*:</td>
    <td><label>
      <input name="password2" type="password" id="password2" value="">
    </label>
      <span class="error">${errors.password2}</span></td>
  </tr>
  <tr>
    <td><input name="registerButton" type="submit" value="Register"></td>
    <td>&nbsp;</td>
  </tr>
</table>
</form>
Already have a login? <a href="login.jsp">Login on MiniBlog</a>
<%	
	session.invalidate();
	String errorMessage = (String) request.getAttribute("DbError");
 	if(errorMessage!=null)
 	{
 		out.print("<script> alert(\"" + errorMessage + "\") </script>");
 		request.removeAttribute("DbError");
 	} 
 %>
</body>
</html>