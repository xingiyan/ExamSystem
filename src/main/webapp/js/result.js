$(function () {
    var TestID = $("#TestID").html();
    var StudentID = $("#StudentID").html();
    var sju = false;
    if(StudentID!=null&&TestID!=null){
        sju=true;
    }
    if(sju){
        var data = JSON.stringify({
            action:"chengji",
            datatext:StudentID,
            datatext2:TestID
        })
        window.parent.ws.send(data);
    }
})
function Callback(ev) {
    var data = JSON.parse(ev.data);
    console.log(data)
    switch (data.action) {
        case "chengji":
            var chengji = data.datatext;
            $("#chengji").html(chengji);
            window.parent.layer.closeAll();
            break;
    }
}