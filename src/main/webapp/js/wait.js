$(function () {
    var data = JSON.stringify({
        action:"teststate",
        datatext:$("#textid").html()
    })
    window.parent.ws.send(data);
})
function Callback(ev) {
    var data = JSON.parse(ev.data);
    switch (data.action) {
        case "teststate":
            if(data.data == 1){
                $("#bufftext").html(
                    " <button class=\"layui-btn layui-btn-normal\" onclick=\"gotext()\">立即进入考试</button>"
                );
            }
            break;
        case "start":
            window.parent.$student.attr("src","examination.jsp")
            break;
    }
}
function gotext() {
    window.parent.$student.attr("src","examination.jsp")
}