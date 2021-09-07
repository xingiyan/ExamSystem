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
    </div>
<%--    <div class="box testinfo" style="text-align:center">--%>
<%--        <div>--%>
<%--            <%=testSubject.getTestSubjectNum()%>、<%=testSubject.getTestSubjectTitle()%>>--%>
<%--        </div>--%>
<%--        <div>--%>

<%--        </div>--%>
<%--    </div>--%>
</div>

<%if(testnewsBen.getTestSubject().size() != 0){%>
<div><div style="width: 100%; text-align: center"><%=testnewsBen.getTestMultipleChoiceQuestions()%></div>
    <%for(TestSubject testSubject :testnewsBen.getTestSubject()){
        if(testSubject.getTestSubjectCLass().equals(testSubject.TESTMULTIPLECHOICEQUESTIONS)){%>
    <div class="box testinfo" style="text-align:center">
        <div>
            <%=testSubject.getTestSubjectNum()%>、<%=testSubject.getTestSubjectTitle()%>
        </div>
        <div>
            <%for(String key :testSubject.getTestSubjectOptionalAnswer().keySet()){%>
            <input name="<%=testSubject.getTestSubjectNum()%>" type="checkbox" value="<%=key%>"/><%=key%>、<%=testSubject.getTestSubjectOptionalAnswer().get(key)%>
            <%}%>
        </div>
    </div>
    <%}
    }%>
</div>
<%}%>


<%if(testnewsBen.getTestSubject().size() != 0){%>
    <div><div style="width: 100%; text-align: center"><%=testnewsBen.getTestSinglechoiceQuestions()%></div>
<%for(TestSubject testSubject :testnewsBen.getTestSubject()){
    if(testSubject.getTestSubjectCLass().equals(testSubject.TESTSINGLECHOICEQUESTIONS)){%>
        <div class="box testinfo" style="text-align:center">
            <div>
                <%=testSubject.getTestSubjectNum()%>、<%=testSubject.getTestSubjectTitle()%>
            </div>
            <div>
                <%for(String key :testSubject.getTestSubjectOptionalAnswer().keySet()){%>
                <input name="<%=testSubject.getTestSubjectNum()%>" type="radio" value="<%=key%>"/><%=key%>、<%=testSubject.getTestSubjectOptionalAnswer().get(key)%>
                <%}%>
            </div>
        </div>
        <%}
}%>
    </div>
<%}%>

<div class="top">
    剩余时间:<div><a id="time" style="color:#fff;">null</a></div>
    <div>
        <%  int i = 1;
            for(TestSubject testSubject :testnewsBen.getTestSubject()){%>
        <div id="<%=testSubject.getTestSubjectNum()%>" class="ka">
            <%=i%>
        </div>

        <%i++;}%>
    </div>
    <button class="layui-btn layui-btn-lg layui-btn-radius layui-btn-norma" style="width: 100px" id="tijiao">提交</button>
    <div>您还有<a id="surplus" style="color: #fff">null</a>道题未作答</div>
</div>

<div class="shield" id="shield">
    <div class="yz">
    </div>
</div>
<script src="<%=path%>/layui/layui.js"></script>
<script src="<%=path%>/js/examination.js"></script>
</body>

</html>