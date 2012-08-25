<%@page import="waps.hw.vibhas.Utilities"%>
<%@page import="waps.hw.vibhas.Message"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome on MiniBlog</title>
</head>
<body>
	<%@ include file="header.html"%>
	<br>
	<div id="main">
	<a>${name}</a>
	<a id="logout" href="logout">Logout</a>
	<form action="home" method="post">
		<label><textarea name="post" id="post" rows="3"
				 onfocus="this.value=''" onblur="this.value='Do you want to say something?'">Do you want to say something?</textarea>
		</label>
		<!--label>Your post: <input name="post" type="text" id="post" value="${param.post}"></label-->
		<span class="error">${errors.post}</span><br> <input
			id="postButton" name="submitButton" type="submit" value="Submit">
	</form>
	<br>
	<%
		if (!Utilities.checkSessionForUser(session)) {
			response.sendRedirect("login.jsp");
		} else {
			String errorMessage = (String) session.getAttribute("Error");
			if (errorMessage != null) {
				out.print("<script> alert(\"" + errorMessage
						+ "\") </script>");
				session.removeAttribute("Error");
			}
			ArrayList<Message> messageList = (ArrayList<Message>) request
					.getAttribute("messageList");
			if (messageList != null) {
				for (int i = 0; i < messageList.size(); i++) {
					Message message = messageList.get(i);
					//out.print("<p>" + message.getContent() + " by " + message.getName() + " at " + message.getTimeStamp() + "</p>");
	%>
	<table class="post_1">
	<tr>
		<td class="name"><%=message.getName()%></td>
		<td class="time"><%=message.getTimeStamp()%></td>
		</tr>
		</table>
		<table class="post_2">
		<tr>
		<td class="content"><%=message.getContent()%></td>
		<td class="deleteForm">
			<form action="delete" method="post">
				<input name="messageID" type="hidden" value="<%=message.getId()%>">
				<input class="deleteButton" name="deleteButton" type="submit"
					value=""><br>
			</form>
		</td>
		</tr>
	</table>
	<%
		}
			}
		}
	%>
	</div>
</body>
</html>