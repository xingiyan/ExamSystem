var Studex = false;
var StudexID = "";
var kanume = $(".ka").length;
var TestID = $("#TestID").html();
$(function(){
    if($("#StudentID").html() != "null"){
        Studex = true;
        StudexID=$("#StudentID").html();
        var data = JSON.stringify({
            action:"underway",
            data:[StudexID,TestID],
        })
        $.post(window.parent.path+"/Student",{request:'getanswer',StudexID:StudexID,TestID:TestID},function (data) {
            var $input = $("input");
            console.log(data)
            if(data != null){
                for(i = 0;i<data.length;i++){
                    for(var key in data[i]){
                        var map = data[i];
                        var list = map[key];
                        for(z = 0;z<list.length;z++){
                            for(j = 0;j<$input.length;j++){
                                if($input[j].name == key && $($input[j]).val() == list[z]){
                                    $input[j].checked= true;
                                }
                            }
                        }
                    }
                }
            }
            kad();
        },"json")

    }


    window.parent.ws.send(data);
    $("input").click(function () {
        if(Studex){
            var data = JSON.stringify({
                action:"answers",
                datatext:StudexID,
                datatext2:TestID,
                data:kad()
            })
            window.parent.ws.send(data);
        }
    })
    $("#tijiao").click(function () {
        if(Studex){
            if(kanume != 0){
                window.parent.layer.confirm('您还有'+kanume+'未答题是否确定提交？', {
                    btn: ['立即提交','返回'] //按钮
                }, function(){
                    tijiaotext();
                    window.parent.layer.closeAll();
                    window.parent.heiping();
                });
            }else{
                window.parent.layer.confirm('您确定提交？', {
                    btn: ['立即提交','返回'] //按钮
                }, function(){
                    tijiaotext();
                    window.parent.layer.closeAll();
                    window.parent.heiping();
                });
            }
        }
    })
})
function tijiaotext() {
    var data = JSON.stringify({
        action:"tijiaotext",
        datatext:StudexID,
        datatext2:TestID
    })
    window.parent.ws.send(data);
}
function Callback(ev) {
    var data = JSON.parse(ev.data);
    switch (data.action) {
        case "time":
            var time = parseInt(data.datatext);
            $("#time").html(formatDuring(time));
            break;
        case "tijiaotext":
            window.parent.$student.attr("src","result.jsp")
            break;
        case "submit":
            window.parent.$student.attr("src","result.jsp")
            break;
        case "violation":
            window.parent.layer.alert("您已被警告违纪，请注意考场纪律")
            break
        case "suspend":
            window.parent. layer.msg('考试已暂停', {icon: 16, shade: 0.3, time:0});
            break;
            case "continue":
                window.parent.layer.closeAll();
            break
    }
}
function formatDuring(mss) {
    var hours = parseInt((mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
    var minutes = parseInt((mss % (1000 * 60 * 60)) / (1000 * 60));
    var seconds = (mss % (1000 * 60)) / 1000;
    return  hours + " : " + minutes + " : " + seconds;
}
window.onload = function () {
    $("#surplus").html($(".ka").length)
}
function kad() {
    var ka = $(".ka");
    var kanum= 0;
    var answers = [];
    for(i = 0;i < ka.length;i++){
        if($("input[name='"+ka[i].id+"']:checked").val() != null){
            $(ka[i]).css("background","#8D8D8D");
            if(Studex){
                var answ = $("input[name='"+ka[i].id+"']:checked");
                var answe = [];
                for(j = 0; j < answ.length;j++){
                    answe.push($(answ[j]).val());
                }
                var answer ={[ka[i].id]:answe};
                answers.push(answer);
            }
            kanum++;
        }else{
            $(ka[i]).css("background","");
        }
    }
    kanume = ka.length-kanum;
    $("#surplus").html(kanume)
    return answers;
}