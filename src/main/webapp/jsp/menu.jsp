<%@ page import="com.lwx.object.Admin" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path =  request.getContextPath();
    Admin admin = (Admin)request.getSession().getAttribute("Admin");
%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>鉴定站考试系统</title>

  <style>
  html{background-color:#E3E3E3; font-size:14px; color:#000; font-family:Helvetica Neue,Helvetica,PingFang SC,Tahoma,Arial,sans-serif}
  a,a:hover{ text-decoration:none;}
  pre{font-family:Helvetica Neue,Helvetica,PingFang SC,Tahoma,Arial,sans-serif}
  .box{padding:20px; background-color:#fff; margin:50px 100px; border-radius:5px;}
  .box a{padding-right:15px;}
  #about_hide{display:none}
  .layer_text{background-color:#fff; padding:20px;}
  .layer_text p{margin-bottom: 10px; text-indent: 2em; line-height: 23px;}
  .button{display:inline-block; *display:inline; *zoom:1; line-height:30px; padding:0 20px; background-color:#56B4DC; color:#fff; font-size:14px; border-radius:3px; cursor:pointer; font-weight:normal;}
  .photos-demo img{width:200px;}
  </style>
  <script src="<%=path%>/js/jquery-3.6.0.js"></script>
  <link rel="stylesheet" href="<%=path%>/layui/css/layui.css">
</head>
<body>
<div class="box" style="text-align: center;">
  <strong><a style="font-size: 25px;"><%=admin.getName()%>职业技能鉴定中心-鉴定站考试系统欢迎您！</a></strong>
  <button class="layui-btn layui-btn-lg layui-btn-radius layui-btn-norma" id="newTest" >新建考试</button>
  当前时间：<a id="time"></a>
  <br/>
  欢迎您使用鉴定站考试系统，你可以<a style="font-size: 25px">新建考试</a>按钮来安排考试，如果在使用中，遇到问题，请点击帮助来获取帮助。
</div>
<div class="box" style="text-align:center">
  <a href="javascript:;" id="help">帮助</a>
</div>
</body>
<script src="<%=path%>/layui/layui.js"></script>
<script src="<%=path%>/js/menu.js"></script>
</html>