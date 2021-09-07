<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path =  request.getContextPath();
%>
<html>
<head>
    <meta charset="UTF-8">
    <script src="<%=path%>/js/jquery-3.6.0.js" ></script>
    <link rel="stylesheet" type="text/css" href="<%=path%>/css/sign.css">
    <title>考试后台系统登录</title>
</head>
<body>
<input type="hidden" value="<%=path%>" id="path" >
    <div class="bg">
        <div class="sign">
                <input type="hidden" value="sign1" id="action" name="action">
                <div class="date">
                    <a class="logo">欢迎登录</a>
                    <div class="username-div" >
                        <img src="<%=path%>/image/loginIco.png">
                        <input type="text" placeholder="USERNAME" class="username" name="acc" id="text_acc">
                    </div>
                    <div class="username-div">
                        <img src="<%=path%>/image/login_pwd.png" style="opacity: 0.3;">
                        <input type="password" placeholder="PASSWORD" class="username" name="pwd" id="text_pass" >
                    </div>
                    <div class="form_btns">
                        <button id="sign">登录</button>
                    </div>
                </div>
        </div>
    </div>
    <div class="shield" id="shield">
        <div class="yz">
            <a>请输入验证码</a>
            <div>
                <input type="text" id="VerificationCodeTest"/>
                <img src="" id="Testimg"/>
                <button id="determine">确定</button>
                <button id="cancel">取消</button>
            </div>

        </div>
    </div>
</body>
<script type="text/javascript" src="<%=path%>/js/recharge.js" ></script>
</html>
