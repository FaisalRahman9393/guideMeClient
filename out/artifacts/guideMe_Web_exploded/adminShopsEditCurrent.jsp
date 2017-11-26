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
<% String nameOfShop=(String)request.getAttribute("nameOfShop"); %>
<% String locationOfShop=(String)request.getAttribute("locationOfShop"); %>
<% String openingHoursOfShop=(String)request.getAttribute("openingHoursOfShop"); %>
<% String infoShop=(String)request.getAttribute("infoShop"); %>

<h1>Welcome Admin</h1>
<h2>You can use the form below to edit information about current shops </h2>
<h3>Please use the form below: </h3>

<form name="shopFormEditUpdate" method="post" action="Shops">
    <input type="hidden" name="pagename" value="shopFormEditUpdate"/>
    Shop Name:          <input size="35" type = "text" name = "shopName" value= "<%=nameOfShop%>"/><br />
    Shop Location:          <input size="35" type = "text" name = "shopLocation" value= "<%=locationOfShop%>"/><br />
    Shop Opening Hours:    <input size="35" type = "text" name = "shopOpeningHours" value= "<%=openingHoursOfShop%>"/><br />
    Shop Information: <input size="35" type = "text" name = "shopInfo" value= "<%=infoShop%>"/><br/>
    <button type="submit">Save Changes</button><br />
</form>

</body>
</html>
