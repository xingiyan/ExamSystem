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
    <title>成绩</title>
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
    <table style="margin: auto; padding: 0">
        <tr>
            <td>科目代码：</td>
            <td id="TestID"><%=testnewsBen.getTestID()%></td>
            <td>考试时间：</td>
            <td><%=testnewsBen.getTesttime()%></td>
            <td>考试时间：</td>
            <td><%=testnewsBen.getTesttime()%></td>
            <td>考试类型：</td>
            <td><%=testnewsBen.getTestClass()%></td>
            <td>准考证号：</td>
            <td id="StudentID" ><%=studentBen == null?null:studentBen.getStudentID()%></td>
            <td>考生姓名：</td>
            <td><%=studentBen == null?null:studentBen.getStudentName()%></td>
        </tr>
    </table>
    <a>科目成绩:</a><a id="chengji">读取中,请稍后</a>
</div>
<script src="<%=path%>/layui/layui.js"></script>
<script src="<%=path%>/js/result.js">
</script>
</body>


</html>