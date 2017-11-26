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
<h2>You have now been sent a 6 digit code to your email address</h2>
<h2>Enter it below with your preferred password: </h2>
<h3>Note: If you have not received an email, please check your junk folder. </h3>

<form name="regreg" method="post" action="RegMe">
    <input type="hidden" name="pagename" value="regreg"/>
    <table>
        <tr>
            Secret Pin: <input name="secPin" required="required" type="text" id="secPin" /><p>

            Password:  <input name="password" required="required" type="password" id="password" /><p>
            Confirm Password: <input name="password_confirm" required="required" type="password" id="password_confirm" oninput="check(this)" /><p>
            <script language='javascript' type='text/javascript'>
                function check(input) {
                    if (input.value != document.getElementById('password').value) {
                        input.setCustomValidity('Make sure the two passwords are matching!');
                    } else {
                        // input is valid -- reset the error message
                        input.setCustomValidity('');
                    }

                }
            </script>
            <br /><br />
            <input type="submit" />
        </tr>
    </table>
</form>
</body>
</html>