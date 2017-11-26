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
<h2>You can use the form below to add new academics </h2>

<form name="adminForm" method="post" action="Profile">
    <input type="hidden" name="pagename" value="adminForm"/>
    Academic name:          <input size="35" type = "text" name = "academicName" value= ""/><br />
    Academic Role:          <input size="35" type = "text" name = "academicRole" value= ""/><br />
    Academic Department:    <input size="35" type = "text" name = "academicDepartment" value= ""/><br />
    Academic Email Address: <input size="35" type = "text" name = "academicEmail" value= ""/><br/>
    Academic Office:        <input size="35" type = "text" name = "academicOffice" value= ""/><br />
    Academic Number:        <input size="35" type = "text" name = "academicNumber" value= ""/><br />
    Academic Availability:  <textarea rows="4" cols="50" name = "academicAvailability"></textarea><br />
    <button type="submit">Save Changes</button><br />
</form>
</body>
</html>
