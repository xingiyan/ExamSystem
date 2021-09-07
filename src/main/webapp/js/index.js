$(function () {
    var path = window.document.location.pathname.substring(0, window.document.location.pathname.substr(1).indexOf('/') + 1);
    $("#upload").click(function () {

        var rea = new FileReader();
        var FIle = $("#file")[0].files[0];
        rea.onloadend = function(e){
            $.post(path+"/Background",{request: "file",FileName:FIle.name.split(".")[0],FileDate:e.target.result,FileClass:FIle.name.split(".")[1]
            },function (data,status,xhr) {
                handle(data);
                },"json")


        }
        rea.readAsBinaryString($("#file")[0].files[0]);
    })

    $("#login").click(function () {

        // console.log( $("file").files[0])
        // console.log(new FormData("E:\\JAVA\\project.config.json"))
        // console.log()
        var $acc = $("#acc").val();
        var $pwd = $("#pwd").val();
        console.log($acc+"::"+$pwd)
        $.post(path+"/Background",{request:"Sign",acc:$acc,pwd:$pwd},function (data,status,xhr) {
            handle(data);
        },"json")

    })
})
function handle(data) {
    switch (data.action) {
        case "Sign":
            if(data.result ==true){
                alert("登录成功");
            }else{
                alert("登录失败")
            }
            break;
        case "file":
            if(data.result ==true){
                alert("传输成功");
            }else{
                alert("传输失败");
            }
            break;
    }
}
