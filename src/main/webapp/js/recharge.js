$(function () {
    var path = window.document.location.pathname.substring(0, window.document.location.pathname.substr(1).indexOf('/') + 1);

    $("#USERNAME").change(function () {
        console.log(12312)
    })
    $("#sign").click(function () {
        var testing = /^[0-9A-Za-z]{6,}$/;
        var accString = $("#text_acc").val();
        var pwdString = $("#text_pass").val();
        if(testing.test(accString)||testing.test(pwdString)){
            $("#VerificationCodeTest").val("")
            $("#Testimg").attr("src",path+"/DocumentControl?request=VerificationCode&"+Math.random());
            $("#shield").css("display","block")
        }else if(accString.length<6){
            alert("账号不能小于6位");
        }else if(pwdString.length<6){
            alert("密码不能小于6位");
        }else if(testing.test(accString)){
            alert("账号只能存在数字，大小写字母")
        }else if(testing.test(pwdString)){
            alert("密码只能存在数字，大小写字母")
        }
    })
    $("#Testimg").click(function () {
        $("#Testimg").attr("src",path+"/DocumentControl?request=VerificationCode&"+Math.random());
    })
    $("#determine").click(function () {
        var codeString = $("#VerificationCodeTest").val();
        if(codeString.length >=4){
            var accString = $("#text_acc").val();
            var pwdString = $("#text_pass").val();
            $.post(path+"/Background",{request:"code",code:codeString},function (data,status,xhr) {
                if(data.result){
                    $.post(path+"/Background",{request:"Sign",acc:accString,pwd:pwdString},function (data,status,xhr) {
                        if(data.datatext =="SuccessfulLogin"){
                            window.location.href=path+'/jsp/menu.jsp';
                        }else if(data.datatext =="AccountDoesNotExist"){
                            alert("账号不存在")
                        }else if(data.datatext =="PasswordError"){
                            alert("密码错误");
                        }else if(data.datatext =="AccountPasswordIsEmpty"){
                            alert("账号密码不能为空且不能小于6位")
                        }
                        $("#shield").css("display","none")
                        $("#VerificationCodeTest").val("")
                        $("#Testimg").attr("src",path+"/DocumentControl?request=VerificationCode&"+Math.random());
                    },"json")
                }else{
                    $("#VerificationCodeTest").val("")
                    $("#Testimg").attr("src",path+"/DocumentControl?request=VerificationCode&"+Math.random());
                    alert("验证码错误！！")
                }
            },"json")
        }else{
            alert("验证码不能为空且必须4位")
        }
    })
    $("#cancel").click(function () {
        $("#shield").css("display","none")
    })
})

