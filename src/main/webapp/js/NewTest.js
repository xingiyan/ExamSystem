$(function () {
    var newtest = false;
    var layer;
    var element;
    var path = window.document.location.pathname.substring(0, window.document.location.pathname.substr(1).indexOf('/') + 1);
    layui.use(['layer','element'], function(){ //独立版的layer无需执行这一句
        layer = layui.layer; //独立版的layer无需执行这一句
        element = layui.element;

    })
    $("#newTest").click(function () {
        if($("#file")[0].files[0] != null) {
            var FIle = $("#file")[0].files[0];
            if (FIle.name.split(".")[1] == "zip") {
                console.log("进入文件上传准备")
                element.progress("Filedemo", "0%");
                $("#shield").css("display", "block");
                var Fileonly = true;
                var bytesPerPiece = 1024 * 1024; // 每个文件切片大小定为1MB .
                var totalPieces;
                var start = 0;
                var end;
                var index = 0;
                var filesize = FIle.size;
                var filename = FIle.name;
                var rea = new FileReader();
                totalPieces = Math.ceil(filesize / bytesPerPiece);
                //计算文件切片总数
                rea.onloadend = function upload(e) {
                    if (start < filesize) {
                        end = start + bytesPerPiece;
                        if (end > filesize) {
                            end = filesize;
                        }
                        var chunk = e.target.result.slice(start, end);//切割文件
                        $.post(path + "/DocumentControl", {
                            request: "file",
                            file: chunk,
                            filename: filename,
                            filesize: filesize,
                            Fileonly: Fileonly
                        }, function (data, status, xhr) {
                            start = end;
                            index++;
                            Fileonly = false;
                            console.log("文件上传:" + start + "/" + filesize)
                            element.progress("Filedemo", parseInt((start / filesize) * 100) + "%");
                            upload(e);
                        }, "json")
                    } else {
                        console.log("传输完成")
                        $.post(path + "/Background",{
                            request: "Getinformation",
                            filename: filename
                        },function (data) {
                            ZipPassword(data);
                        },"json")
                        setTimeout(function () {
                            $("#shield").css("display", "none");
                        }, 500)

                    }
                }
                rea.readAsBinaryString(FIle);
            } else {
                layer.alert("请选择考试所需的zip压缩包！！")
            }
        }else{
            layer.alert("请选择考试所需的zip压缩包！！");
        }
    })
    function ZipPassword(data) {
        switch (data.datatext) {
            case "OperationSuccessful":
                newtest = true;
                var testn = data.data;
                $("#testName").html(testn.testID)
                $("#test").html(testn.testName)
                $("#testtime").html(testn.testtime)
                $("#testClass").html(testn.testClass)
                $("#testtype").html(testn.testtype)
                $("#testCompany").html(testn.testCompany)
                $("#testGrade").html(testn.testGrade)
                $("#testpass").html(testn.testpass)
                $("#testNum").html(testn.studentBens.length)
                layer.closeAll();
                break;
            case "PasswordISrequired":
                layer.prompt({
                    formType: 2,
                    value: '',
                    title: '请输入密码',
                    area: ['250px', '25px'] //自定义文本域宽高
                }, function(value, index, elem){
                    layer.close(index);
                    $.post(path + "/Background",{
                        request: "Getinformation",
                        filename: $("#file")[0].files[0].name,
                        filePassword:value
                    },function (data) {
                        ZipPassword(data);
                    },"json")
                    // layer.closeAll();
                    heiping();
                });
                break;
            case "WrongPassword":
                layer.prompt({
                    formType: 2,
                    value: '',
                    title: '密码错误，请重新输入',
                    area: ['250px', '25px'] //自定义文本域宽高
                }, function(value, index, elem){
                    layer.close(index);
                    $.post(path + "/Background",{
                        request: "Getinformation",
                        filename: $("#file")[0].files[0].name,
                        filePassword:value
                    },function (data) {
                        ZipPassword(data);
                    },"json")
                    // layer.closeAll();
                    heiping();
                });
                break;
        }
    }
    $("#previewTest").click(function () {
        if(newtest){
            window.location.href=path+'/jsp/examination.jsp';
        }else{
            layer.alert("请先导入试卷后预览");
        }
    })
    $("#CanInfo").click(function () {
        if(newtest){
            window.location.href=path+'/jsp/StudentList.jsp';
        }else{
            layer.alert("请先导入试卷后预览");
        }
    })
    $("#confirmTest").click(function () {
        if(newtest){
            window.location.href=path+'/jsp/AdminTest.jsp';
        }else{
            layer.alert("请先导入试卷后预览");
        }
    })
})
function heiping(){
    layer.msg('等待服务器响应', {icon: 16, shade: 0.3, time:0});
}