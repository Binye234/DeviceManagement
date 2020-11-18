<%--
  Created by IntelliJ IDEA.
  User: tianj
  Date: 2020/11/10
  Time: 16:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>

<head>

    <title>恒利德公司漏扫设备管理系统</title>


    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <script
            type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
    <link rel="stylesheet" href="static/themes/login.css">
    <!-- Custom Theme files -->

    <!-- //Custom Theme files -->
</head>
<%
   Object error=request.getAttribute("error");
   String msg;
   if(error==null){
        msg="";
   }else {
        msg=(String)error;
   }

%>

<body>
<!-- main -->
<div class="main-w3layouts wrapper">
    <h1>恒利德公司漏扫设备管理系统</h1>
    <div class="main-agileinfo">
        <div class="agileits-top">
            <form action="/loginservlet" method="post">
                <input class="text" type="text" name="username" placeholder="用户名" required="">
                <input class="text" type="password" name="password" placeholder="密码" required="">
                <p style="color: #CC2222"><%=msg %></p>
                <input type="submit" value="登录">
            </form>

        </div>
    </div>
    <!-- copyright -->

    <!-- //copyright -->
    <ul class="w3lsg-bubbles">
        <li></li>
        <li></li>
        <li></li>
        <li></li>
        <li></li>
        <li></li>
        <li></li>
        <li></li>
        <li></li>
        <li></li>
    </ul>
</div>
<!-- //main -->
</body>

</html>