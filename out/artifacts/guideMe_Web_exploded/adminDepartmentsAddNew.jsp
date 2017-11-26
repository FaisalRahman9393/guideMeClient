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
<h2>You can use the form below to add departments </h2>
<h3>Please populate the form about the new department</h3>

<form name="departmentsFormAddNew" method="post" action="Departments">
    <input type="hidden" name="pagename" value="departmentsFormAddNew"/>
    Department name:          <input size="35" type = "text" name = "departmentName" value= ""/><br />
    <font color="red">Please note: Make sure that location is a valid google maps address.</font>
    Department location:          <input size="35" type = "text" name = "departmentLocation" value= ""/><br />
    Department Faculty:    <input size="35" type = "text" name = "departmentFaculty" value= ""/><br />
    Department information:  <textarea rows="4" cols="50" name = "departmentInfo"></textarea><br />
    <button type="submit">Save Changes</button><br />
</form>

</body>
</html>
