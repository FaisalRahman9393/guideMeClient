<%--
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
<% String nameOfAcademic=(String)request.getAttribute("nameOfAcademic"); %>
<% String roleOfAcademic=(String)request.getAttribute("roleOfAcademic"); %>
<% String departmentOfAcademic=(String)request.getAttribute("departmentOfAcademic"); %>
<% String emailOfAcademic=(String)request.getAttribute("emailOfAcademic"); %>
<% String officeOfAcademic=(String)request.getAttribute("officeOfAcademic"); %>
<% String numberOfAcademic=(String)request.getAttribute("numberOfAcademic"); %>
<% String availabilityOfAcademic=(String)request.getAttribute("availabilityOfAcademic"); %>

<h1><%=nameOfAcademic%>'s profile</h1>
<h2>Welcome to Guide Me <%=nameOfAcademic%>. </h2>
<h2>You can use the form below to update your information </h2>

<form name="profileForm" method="post" action="Profile">
    <input type="hidden" name="pagename" value="profileForm"/>
    Your name:          <input size="35" type = "text" name = "academicName" value= "<%=nameOfAcademic%>"/><br />
    Your Role:          <input size="35" type = "text" name = "academicRole" value= "<%=roleOfAcademic%>"/><br />
    Your Department:    <input size="35" type = "text" name = "academicDepartment" value= "<%=departmentOfAcademic%>"/><br />
    Your Email Address: <input size="35" type = "text" name = "academicEmail" value= "<%=emailOfAcademic%>"/><br/>
    Your Office:        <input size="35" type = "text" name = "academicOffice" value= "<%=officeOfAcademic%>"/><br />
    Your Number:        <input size="35" type = "text" name = "academicNumber" value= "<%=numberOfAcademic%>"/><br />
    Your Availability:  <textarea rows="4" cols="50" name = "academicAvailability"> <%=availabilityOfAcademic%></textarea><br />
    <button type="submit">Save Changes</button><br />
</form>
</body>
</html>
