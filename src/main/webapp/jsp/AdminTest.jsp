<%@ page import="com.lwx.object.Admin" %>
<%@ page import="com.lwx.bean.TestnewsBen" %>
<%@ page import="com.lwx.bean.StudentBen" %>
<%@ page import="com.lwx.bean.TestSubject" %>
<%@ page import="java.util.HashMap" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path =  request.getContextPath();
    Admin admin = (Admin)request.getSession().getAttribute("Admin");
    TestnewsBen testnewsBen = (TestnewsBen)request.getSession().getAttribute("test");
    StudentBen studentBen = (StudentBen)request.getSession().getAttribute("studentBen");
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
    <link rel="stylesheet" href="<%=path%>/css/examinatio.css">
</head>
<body>
<div class="box" style="text-align: center;">
    <strong><a style="font-size: 25px;">职业技能鉴定国家题库</a></strong><br/>
    <a><%=testnewsBen.getTestName()%></a>
    <div style="margin-left: auto; margin-right: auto">
        <table style="margin: auto; padding: 0">
            <tr><td>考试名称:</td><td><a id="testName"><%=testnewsBen.getTestID()%></a></td><td>考试试卷:</td><td><a id="test"><%=testnewsBen.getTestName()%></a></td></tr>
            <tr><td>试卷时间:</td><td><a id="testtime"><%=testnewsBen.getTesttime()%></a></td><td>考试类型:</td><td><a id="testClass"><%=testnewsBen.getTestClass()%></a></td></tr>
            <tr><td>鉴定工种:</td><td><a id="testtype"><%=testnewsBen.getTesttype()%></a></td><td>鉴定机构:</td><td><a id="testCompany"><%=testnewsBen.getTestCompany()%></a></td></tr>
            <tr><td>鉴定等级:</td><td><a id="testGrade"><%=testnewsBen.getTestGrade()%></a></td><td>及格分数:</td><td><a id="testpass"><%=testnewsBen.getTestpass()%></a></td></tr>
            <tr><td>考生人数:</td><td><a id="testNum"><%=testnewsBen.getStudentBens().size()%></a></td><td>当前状态:</td><td><a id="teststate">等待开始考试</a></td></tr>
            <tr><td>剩余时间:</td><td><a id="testsurplustime">--:--</a></td></tr>
        </table>
    </div>
    <div>
        <button class="layui-btn layui-btn-sm" id="start">开始考试</button>
        <button class="layui-btn layui-btn-sm" id="submit">强制交卷</button>
        <button class="layui-btn layui-btn-sm" id="cheat">作弊</button>
        <button class="layui-btn layui-btn-sm" id="violation">违纪</button>
        <button class="layui-btn layui-btn-sm" id="suspend">暂停考试</button>
        <button class="layui-btn layui-btn-sm" id="export">导出成绩</button>

    </div>
</div>
<div class="box" style="text-align:center;float: left">
    <a style="font-size: 20px;">考生列表：</a>全选:<input type="checkbox" id="Select">
    <div style="float: none"></div>
    <div>
    <%for(StudentBen studentBen1 : testnewsBen.getStudentBens()){%>
        <div style=" margin: 5px;box-shadow: 0 2px 10px 0 rgb(0 0 0 / 10%);width: 140px;height: 150px;background: #9F9F9F;float: left;padding: 5px;border-radius: 15px" id="<%=studentBen1.getStudentID()%>">
            <input type="checkbox" value="<%=studentBen1.getStudentTestID()%>" name="student" id="<%=studentBen1.getStudentID()%>">
            <div>准考证号:<%=studentBen1.getStudentID()%></div>
            <div>姓名:<%=studentBen1.getStudentName()%></div>
            <div>状态:<a id="<%=studentBen1.getStudentID()%>al">等待登录</a></div>
        </div>
    <%}%>
    </div>
</div>
<div class="top">

</div>

<div class="shield" id="shield">
    <div class="yz">
    </div>
</div>
<script src="<%=path%>/layui/layui.js"></script>
<script src="<%=path%>/js/AdminSocket.js"></script>
</body>

</html>