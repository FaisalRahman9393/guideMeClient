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
    <title>Registration Form </title>
</head>
<body>
<h1> Guide Me - Registration process </h1>
<h2>In order to confirm that you're an academic at Lancaster university, please insert your university email below: </h2>

<form name="regmail" method="post" action="RegMe">
    <input type="hidden" name="pagename" value="regmail"/>
    <table>
        <tr>
            <td>Lancaster university e-mail: </td>
            <td><input type="text" name="txtUserName"/></td>
        </tr>
        <tr>
            <td><button type="submit">Submit</button>
            </td>
        </tr>
    </table>
</form>



</body>
</html>