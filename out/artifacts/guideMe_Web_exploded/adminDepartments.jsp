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
<h2>You can use the form below to add or edit departments </h2>
<h3>Please choose one of the following: </h3>

<form name="departmentsFormEditPressed" method="post" action="Departments">
    <input type="hidden" name="pagename" value="departmentsFormEditPressed"/>
    <button type="submit">Edit current departments</button><br />
</form>


<form action="adminDepartmentsAddNew.jsp">
    <input type="submit" value="Add a new department" />
</form>

</body>
</html>
