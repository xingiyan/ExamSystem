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
    <strong><a style="font-size: 25px;">厦门市职业技能鉴定中心-学生考试系统</a></strong>
    <div>
        <div class="layui-form-item">
            <label class="layui-form-label">考试名称:</label>
            <div class="layui-input-block">
                <select name="city" lay-verify="" id="testName" class="layui-input" id="testID">
                    <option value="">请选择一个考试</option>

                </select>
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">准考证号:</label>
            <div class="layui-input-block">
                <input type="text" name="title" required  lay-verify="required" placeholder="请输入准考证号" autocomplete="off" class="layui-input" id="studentNum">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">身份证号:</label>
            <div class="layui-input-block">
                <input type="text" name="title" required  lay-verify="required" placeholder="请输入身份证号" autocomplete="off" class="layui-input" id="studentID">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">姓名:</label>
            <div class="layui-input-block">
                <input type="text" name="title" required  lay-verify="required" placeholder="请输入姓名" autocomplete="off" class="layui-input" id="studentName">
            </div>
        </div>
        <button type="button" class="layui-btn layui-btn-fluid" id="sign">登录</button>
    </div>
</div>
</body>
<script type="text/javascript" src="<%=path%>/js/studentsign.js"></script>
<script type="text/javascript">

</script>
</html>