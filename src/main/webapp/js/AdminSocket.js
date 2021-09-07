$(function () {
    var layer;
    var textsta = false;
    layui.use(['layer'], function(){ //独立版的layer无需执行这一句
        layer = layui.layer; //独立版的layer无需执行这一句
    })
    var testnew = $("#testName").html();
    var path = window.document.location.pathname.substring(0, window.document.location.pathname.substr(1).indexOf('/') + 1);
    var ws = null;
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
        var data = JSON.stringify({
            action:"identity",
            data:["admin",testnew]
        })
        ws.send(data);
    }

    // 收到消息的回调方法
    ws.onmessage = function (ev) {
        var data = JSON.parse(ev.data);
        switch (data.action) {
            case "time":
                var time = parseInt(data.datatext);
                $("#testsurplustime").html(formatDuring(time));
                $("#teststate").html("考试进行中")
                $("#suspend").html("暂停考试")
                textsta=true;
                break;
            case "studenstate":
                var underway = data.data.underway;
                var complete = data.data.complete;
                var cheat = data.data.cheat;
                var Nullstate = data.data.Nullstate;
                for(i=0;i<underway.length;i++){
                    $("#"+underway[i]).css("background","#fff")
                    if(textsta){
                        $("#"+underway[i]+"al").html("考试中")
                    }else{
                        $("#"+underway[i]+"al").html("等待开始考试")
                    }
                }
                for(i=0;i<complete.length;i++){
                    $("#"+complete[i]).css("background","#99ff99")
                    $("#"+complete[i]+"al").html("已完成考试")
                }
                for(i=0;i<cheat.length;i++){
                    $("#"+cheat[i]).css("background","#ff0066")
                    $("#"+cheat[i]+"al").html("作弊")
                }
                for(i=0;i<Nullstate.length;i++){
                    $("#"+Nullstate[i]).css("background","#9F9F9F")
                    $("#"+Nullstate[i]+"al").html("等待登录")
                }
                break;

        }
    }

    // 连接关闭的回调方法
    ws.onclose = function () {
        console.log("WebSocket连接关闭");
    }


    // 监听窗口关闭事件，防止连接没断关闭窗口。
    window.onbeforeunload = function () {
        ws.close();
    }


    // 关闭websocket连接
    function closeWebsocket(){
        ws.close();
    }
    //开始考试
    $("#start").click(function () {
        layer.confirm('是否立即开始本场考试？', {
            btn: ['开始考试','返回'] //按钮
        }, function(){
            var data = JSON.stringify({
                action:"start",
                datatext:testnew
            })
            ws.send(data);
            layer.closeAll();
        });
    })
    //强制交卷
    $("#submit").click(function () {
        layer.confirm('是否立即指定选中的考生交卷？', {
            btn: ['强制交卷','返回'] //按钮
        }, function(){
            var StudenID = [];
            var $AllStudenID = $("input");
            for(i = 0;i<$AllStudenID.length;i++){
                if($AllStudenID[i].checked == true &&$AllStudenID[i].name == "student"){
                    StudenID.push($AllStudenID[i].id)
                }
            }

            if(StudenID.length !=0){
                var data = JSON.stringify({
                    action:"submit",
                    data:StudenID,
                    datatext:testnew
                })
                ws.send(data);
            }else{
                layer.msg('请先选择强制交卷的考生', {icon: 2});
                return;
            }
            layer.closeAll();
        });
    })
    //作弊
    $("#cheat").click(function () {
        layer.confirm('是否立即指定选中的考生判定作弊？', {
            btn: ['确认作弊','返回'] //按钮
        }, function(){
            var StudenID = [];
            var $AllStudenID = $("input");
            for(i = 0;i<$AllStudenID.length;i++){
                if($AllStudenID[i].checked == true &&$AllStudenID[i].name == "student"){
                    StudenID.push($AllStudenID[i].id)
                }
            }
            if(StudenID.length !=0){
                var data = JSON.stringify({
                    action:"cheat",
                    data:StudenID,
                    datatext:testnew
                })
                ws.send(data);
            }else{
                layer.msg('请先选择判定作弊的考生', {icon: 2});
                return;
            }
            layer.closeAll();
        });
    })
    //违纪
    $("#violation").click(function () {
        layer.confirm('是否立即指定选中的考生警告违纪？', {
            btn: ['确认违纪','返回'] //按钮
        }, function(){
            var StudenID = [];
            var $AllStudenID = $("input");
            for(i = 0;i<$AllStudenID.length;i++){
                if($AllStudenID[i].checked == true&&$AllStudenID[i].name == "student"){
                    StudenID.push($AllStudenID[i].id)
                }
            }

            if(StudenID.length !=0){
                var data = JSON.stringify({
                    action:"violation",
                    data:StudenID,
                    datatext:testnew
                })
            }else{
                layer.msg('请先选择判定违纪的考生', {icon: 2});
                return;
            }
            ws.send(data);
            layer.closeAll();
        });
    })
    //暂停考试
    $("#suspend").click(function () {
        if(textsta){
            layer.confirm('是否立即暂停本场考试？', {
                btn: ['暂停考试','返回'] //按钮
            }, function(){
                var data = JSON.stringify({
                    action:"suspend",
                    datatext:testnew
                })
                ws.send(data);
                layer.closeAll();
                $("#teststate").html("考试已暂停")
                $("#suspend").html("继续考试")
                textsta=false;
            });
        }else{
            layer.confirm('是否继续本场考试？', {
                btn: ['继续考试','返回'] //按钮
            }, function(){
                var data = JSON.stringify({
                    action:"continue",
                    datatext:testnew
                })
                ws.send(data);
                $("#teststate").html("考试进行中")
                $("#suspend").html("暂停考试")
                layer.closeAll();
            });
        }
    })
    //导出成绩
    $("#export").click(function () {
        layer.confirm('是否需要导出本场考试成绩？', {
            btn: ['导出成绩','返回'] //按钮
        }, function(){
            window.open(path+"/DocumentControl?request=Excel&TID="+testnew,"_blank","");
            layer.closeAll();
        });
    })
    $("#Select").change(function () {
        var $input = $("input");
        for(i = 0;i < $input.length;i++){
            if($("#Select")[0].checked){
                $input[i].checked=true;
            }else{
                $input[i].checked=false;
            }
        }
    })
    $("input").change(
        function () {
            var $input = $("input");
            for(i = 0;i <$input.length;i++){
                if(!$input[i].checked && $input[i] != $("#Select")[0]){
                    $("#Select")[0].checked=false;
                    return
                }
            }
            $("#Select")[0].checked= true;
        }
    )

    function formatDuring(mss) {
        var hours = parseInt((mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
        var minutes = parseInt((mss % (1000 * 60 * 60)) / (1000 * 60));
        var seconds = (mss % (1000 * 60)) / 1000;
        return  hours + " : " + minutes + " : " + seconds;
    }
})