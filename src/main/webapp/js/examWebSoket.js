var ws = null;
var path = window.document.location.pathname.substring(0, window.document.location.pathname.substr(1).indexOf('/') + 1);
var layer;
var $student;
$(function () {
    layui.use(['layer','element'], function(){ //独立版的layer无需执行这一句
        layer = layui.layer; //独立版的layer无需执行这一句
        element = layui.element;

    })
    var testnew = $("#testName").html();
    $student = $("#student");console.log()
    if ('WebSocket' in window){
        ws = new WebSocket("ws:localhost:8080"+path+"/TestSocket")
    }else {
        alert("浏览器不支持");
    }
    var connBtn = document.getElementById("connBtn");
    var sendBtn = document.getElementById("sendBtn");
    var closeBtn = document.getElementById("closeBtn");

    // 连接安生错误的回调方法
    ws.onerror = function () {
        console.log("连接发生错误");
    }
    // 连接成功的回调方法
    ws.onopen = function (ev) {

    }
    // 收到消息的回调方法
    ws.onmessage = function (ev) {
        window.frames["student"].Callback(ev);
    }
    // 连接关闭的回调方法
    ws.onclose = function () {
        console.log("WebSocket连接关闭");
    }


    // 监听窗口关闭事件，防止连接没断关闭窗口。
    window.onbeforeunload = function () {
        ws.close();
    }
})
function heiping(){
    layer.confirm("等待服务器响应", {
        title: "请稍后",
        icon: 3,
        type: 4,
        closeBtn :0,
        // btn: ['', '']
    }, function (index) {

    });
}