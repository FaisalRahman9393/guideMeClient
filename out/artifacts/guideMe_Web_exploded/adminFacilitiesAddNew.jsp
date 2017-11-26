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
<h1>Welcome Admin</h1>
<h2>You can use the form below to add new facilities </h2>
<h3>Please use the form below </h3>

<form name="facilitiesFormAddNew" method="post" action="Facilities">
    <input type="hidden" name="pagename" value="facilitiesFormAddNew"/>
    Facility name:          <input size="35" type = "text" name = "facilityName" value= ""/><br />
    <font color="red">Please note: Make sure that location is a valid google maps address.</font>
    Facility location:          <input size="35" type = "text" name = "facilityLocation" value= ""/><br />
    Facility Opening hours:    <input size="35" type = "text" name = "facilityOpeningHours" value= ""/><br />
    Facility information:  <textarea rows="4" cols="50" name = "facilityInfo"></textarea><br />
    <button type="submit">Save Changes</button><br />
</form>



</body>
</html>
