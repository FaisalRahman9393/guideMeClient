

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
<% String nameOfFacility=(String)request.getAttribute("nameOfFacility"); %>
<% String locationOfFacility=(String)request.getAttribute("locationOfFacility"); %>
<% String hoursOfFacility=(String)request.getAttribute("hoursOfFacility"); %>
<% String infoFacility=(String)request.getAttribute("infoOfFacility"); %>

<h1>Welcome Admin</h1>
<h2>You can use the form below to edit current departments </h2>
<h3>Please select a department from the drop down and press "GET"</h3>


<form name="facilitiesFormEditUpdate" method="post" action="Facilities">
    <input type="hidden" name="pagename" value="facilitiesFormEditUpdate"/>
    Facility Name: <%=nameOfFacility%><br />
    Facility Location:          <input size="35" type = "text" name = "facilitiesLocation" value= "<%=locationOfFacility%>"/><br />
    Facility Opening hours:    <input size="35" type = "text" name = "facilitiesHours" value= "<%=hoursOfFacility%>"/><br />
    Facility Information: <input size="35" type = "text" name = "facilitiesInfo" value= "<%=infoFacility%>"/><br/>
    <button type="submit">Save Changes</button><br />
</form>

</body>
</html>