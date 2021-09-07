<%--
  Created by IntelliJ IDEA.
  User: LWX
  Date: 2021/6/2
  Time: 15:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@ taglib uri="http://j" %>--%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="<%=basePath%>/js/jquery-3.6.0.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/index.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/css/index.css">
</head>
<body>
<div>
    <input type="text" id="acc"></br>
    <input type="password" id="pwd"></br>
    <button id="login">登录</button>

    用户名：<input type="text" name="userName">  <br/>
    文件：   <input type="file" id="file" accept=".zip">   <br/>
    <button id="upload" >上传</button>
</div>
</body>
</html>
