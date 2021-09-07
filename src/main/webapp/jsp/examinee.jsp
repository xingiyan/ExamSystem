<%--
  Created by IntelliJ IDEA.
  User: LWX
  Date: 2021/6/23
  Time: 16:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.lwx.object.Admin" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path =  request.getContextPath();
    Admin admin = (Admin)request.getSession().getAttribute("Admin");
%>
<html>
<head>
    <title>考生端</title>
    <script src="<%=path%>/js/jquery-3.6.0.js"></script>
</head>
<body style="margin: 0;padding: 0">
<iframe id="student" width="100%" height="100%" scrolling="auto" frameborder="0" src="studentSign.jsp" name="student">

</iframe>
</body>
    <script src="<%=path%>/layui/layui.js"></script>
    <script src="<%=path%>/js/examWebSoket.js"></script>
</html>
