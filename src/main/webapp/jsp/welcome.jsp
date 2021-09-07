<%@ page import="com.lwx.object.Admin" %>
<%@ page import="com.lwx.bean.TestnewsBen" %>
<%@ page import="com.lwx.bean.StudentBen" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path =  request.getContextPath();
    Admin admin = (Admin)request.getSession().getAttribute("Admin");
    TestnewsBen testnewsBen = (TestnewsBen)request.getSession().getAttribute("test");
%>

<!DOCTYPE html>
<html>
<head>

    <meta charset="utf-8">
    <title>鉴定站考试系统</title>
    <script src="<%=path%>/js/jquery-3.6.0.js"></script>
    <link rel="stylesheet" href="<%=path%>/layui/css/layui.css">
    <link rel="stylesheet" href="<%=path%>/css/welcome.css">
    <style>
        td{
            padding: 10px;
        }
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
</head>
<body>
<div class="box testinfo" style="text-align:center">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md6" id="admin">
            <div class="layui-panel dd">
                <div style="padding: 30px;">监考端</div>
            </div>
        </div>
        <div class="layui-col-md6" id="student">
            <div class="layui-panel dd">
                <div style="padding: 30px;">考生端</div>
            </div>
        </div>
    </div>
</div>
<script src="<%=path%>/layui/layui.js"></script>
<script>
    $(function () {
        $("#admin").click(function () {
            window.open("<%=path%>/jsp/Sign.jsp");
        })
        $("#student").click(function () {
            window.open("<%=path%>/jsp/examinee.jsp");
        })
    })
</script>
</body>

</html>