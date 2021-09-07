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
    <link rel="stylesheet" href="<%=path%>/css/NewTest.css">
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
    <div>考生信息<br/>
        <table>
            <tr>
                <td>考生ID</td>
                <td>考生姓名</td>
                <td>考生身份证</td>
                <td>作答试卷ID</td>
            </tr>
            <%for(StudentBen studentBen:testnewsBen.getStudentBens()){%>
                <tr>
                    <td><%=studentBen.getStudentID()%></td>
                    <td><%=studentBen.getStudentName()%></td>
                    <td><%=studentBen.getStudentCitizenID()%></td>
                    <td><%=studentBen.getStudentTestID()%></td>
                </tr>
            <%}%>
        </table>
        <a href="#" onclick="javascript:history.back(-1);">返回上一页</a>
    </div>
</div>
<script src="<%=path%>/layui/layui.js"></script>
</body>

</html>