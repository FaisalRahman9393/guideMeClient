<%--
  Accessable at: 10.32.169.61:8081 (private network:port) OR the (local address: port)
  Created by IntelliJ IDEA.
  User: Faze
  Date: 20/02/2017
  Time: 16:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <title>Log in form</title>
</head>
<body>
<h1> Welcome to Guide Me's online web form </h1>
<h2> Lancaster University's academics can use this form to update their details on the guide me systems across the campus</h2>
<h3> Please log in or if you have not registered, select the register button below.</h3>

<form name="login" method="post" action="LogIn">
  <input type="hidden" name="pagename" value="login"/>
  <table>
    <tr>
      <td>Email address: </td>
      <td><input type="text" name="txtUserName"/></td>
    </tr>
    <tr>
      <td>Password</td>
      <td><input type="password" name="txtPassword"/></td>
    </tr>
    <tr>
      <td><button type="submit">Login</button>
      </td>
    </tr>
  </table>
</form>


<form action="registrationPage.jsp">
    <input type="submit" value="Register with guideMe">
</form>
</body>
</html>