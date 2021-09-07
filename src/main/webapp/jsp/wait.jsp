<%@ page import="com.lwx.object.Admin" %>
<%@ page import="com.lwx.bean.TestnewsBen" %>
<%@ page import="com.lwx.bean.StudentBen" %>
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
    <script src="<%=path%>/js/jquery-3.6.0.js"></script>
    <link rel="stylesheet" href="<%=path%>/layui/css/layui.css">
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
    <table class="layui-table">
        <colgroup>
            <col width="150">
            <col width="200">
            <col>
        </colgroup>
        <thead>
        <tr>
            <th>准考证号:</th>
            <td><%=studentBen.getStudentID()%></td>
            <th></th>
            <td></td>
        </tr>
        </thead>
        <tbody>
        <tr>
            <th>考生姓名:</th>
            <td><%=studentBen.getStudentName()%></td>
            <th>身份证号:</th>
            <td><%=studentBen.getStudentCitizenID()%></td>
        </tr>
        <tr>
            <th>考试科目代号</th>
            <td id="textid"><%=testnewsBen.getTestID()%></td>
            <th>考试科目名称:</th>
            <td><%=testnewsBen.getTestName()%></td>
        </tr>
        <tr>
            <th>考试工种</th>
            <td><%=testnewsBen.getTesttype()%></td>
            <th>考试等级:</th>
            <td><%=testnewsBen.getTestGrade()%></td>
        </tr>
        </tbody>
    </table>
    <div id="bufftext"><i class="layui-icon layui-icon-form"> 正在等待监考人员开启本场考试</i></div>
</div>
<script src="<%=path%>/layui/layui.js"></script>
<script src="<%=path%>/js/wait.js"></script>
<script>
    layui.use('table', function() {
        var table = layui.table;
    })
</script>
</body>

</html>