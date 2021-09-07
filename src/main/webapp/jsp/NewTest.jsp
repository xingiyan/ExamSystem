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
<%--  <script src="<%=path%>/layer-v3.5.1/layer/layer.js"></script>--%>
  <link rel="stylesheet" href="<%=path%>/layui/css/layui.css">
  <link rel="stylesheet" href="<%=path%>/css/NewTest.css">
<%--  <link rel="stylesheet" href="<%=path%>/layui/css/layui.css"  media="all">--%>
</head>
<body>
<div class="box" style="text-align: center;">
  <strong><a style="font-size: 25px;">试卷导入</a></strong><br/>
  试卷压缩包位置：<input  type="file" id="file" class="layui-btn layui-btn-lg layui-btn-radius layui-btn-norma"  accept=".zip"/>
  <button class="layui-btn layui-btn-lg layui-btn-radius layui-btn-norma" id="newTest">导入</button>
</div>
<div class="box testinfo" style="text-align:center">
  <div>试卷信息<br/>
      <table>
        <tr><td>考试名称:</td><td><a id="testName"></a></td><td>考试试卷:</td><td><a id="test"></a></td></tr>
        <tr><td>试卷时间:</td><td><a id="testtime"></a></td><td>考试类型:</td><td><a id="testClass"></a></td></tr>
        <tr><td>鉴定工种:</td><td><a id="testtype"></a></td><td>鉴定机构:</td><td><a id="testCompany"></a></td></tr>
        <tr><td>鉴定等级:</td><td><a id="testGrade"></a></td><td>及格分数:</td><td><a id="testpass"></a></td></tr>
        <tr><td>考生人数:</td><td><a id="testNum"></a></td></tr>
      </table>
    <button class="layui-btn layui-btn-lg layui-btn-radius layui-btn-norma" id="previewTest">预览试卷</button>
    <button class="layui-btn layui-btn-lg layui-btn-radius layui-btn-norma" id="CanInfo">预览考生信息</button>
    <button class="layui-btn layui-btn-lg layui-btn-radius layui-btn-norma" id="confirmTest">确认考试信息</button>
  </div>
</div>
<div class="shield" id="shield">
  <div class="yz">
    <a>文件上传中，请稍后</a>
    <div class="layui-progress layui-progress-big" lay-showPercent="true" lay-filter="Filedemo" id="Filedemo">
      <div class="layui-progress-bar layui-bg-blue" lay-percent="0%" lay-showPercent="true"></div>
    </div>
  </div>
</div>
<script src="<%=path%>/layui/layui.js"></script>
<script src="<%=path%>/js/NewTest.js"></script>
</body>

</html>