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
<h2>You can use the form below to add new shops </h2>
<h3>Please use the form below: </h3>

<form name="shopFormAddNew" method="post" action="Shops">
    <input type="hidden" name="pagename" value="shopFormAddNew"/>
    Shop name:          <input size="35" type = "text" name = "shopName" value= ""/><br />
    <font color="red">Please note: Make sure that location is a valid google maps address.</font>
    Shop location:          <input size="35" type = "text" name = "shopLocation" value= ""/><br />
    Shop Opening hours:    <input size="35" type = "text" name = "shopOpeningHours" value= ""/><br />
    Shop information:  <textarea rows="4" cols="50" name = "shopInfo"></textarea><br />
    <button type="submit">Save Changes</button><br />
</form>

</body>
</html>
