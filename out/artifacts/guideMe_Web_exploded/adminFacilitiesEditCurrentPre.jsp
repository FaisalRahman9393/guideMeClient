

<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:c="http://java.sun.com/jsp/jstl/core">
<%@ page import="java.util.ArrayList" %>

<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Faze
  Date: 20/02/2017
  Time: 16:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Guide Me</title>
</head>
<body>
<h1>Welcome Admin</h1>
<h2>You can use the form below to edit current departments </h2>
<h3>Please select a department from the drop down and press "GET"</h3>

<form name="chosenFacility" method="post" action="Facilities">
    <input type="hidden" name="pagename" value="chosenFacility"/>
    Department Name:
    <select name="selectedFac">
    <% ArrayList list = (ArrayList) request.getAttribute("list");
        for(int i=0;i<list.size();i++)
        { %>
    <option value='<%=list.get(i)%>'><%=list.get(i)%></option> <%
    }%>
    </select>
    <button type="submit">Get information</button><br />
</form>


</body>
</html>
