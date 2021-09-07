function testname(){
    var data = JSON.stringify({
        action:"testname"
    })
    window.parent.ws.send(data);
}
function Callback(ev) {
    var data = JSON.parse(ev.data);
    switch (data.action) {
        case "testname":
            var testnames = "<option value=\"\">请选择一个考试</option>";
            var tests = data.data;
            for(i = 0;i<tests.length;i++){
                testnames =  testnames+"<option value=\""+tests[i].testID+"\">"+tests[i].testName+"</option>";
            }
            $("#testName").html(testnames);
            break;
        case "student":
            if(data.data == null){
                window.parent.layer.alert("登录失败，请重新确认输入的信息")
            }else{
                $.post(window.parent.path+"/Student",{
                    request:"GetTest",
                    TestID:data.data.studentTestID,
                    studentID:data.data.studentID},function (data) {
                    console.log(data)
                    if(data.datatext == "normal"){
                        window.parent.$student.attr("src","wait.jsp")
                    }else{
                        window.parent.$student.attr("src","result.jsp")
                    }
                },"json")
            }
            break;
    }
}
$(function () {
    testname();
    var form;
    var stact = 0;
    $("#sign").click(function () {
        var testID = $("#testName option:selected").val();
        var studentNum = $("#studentNum").val();
        var studentID = $("#studentID").val();
        var studentName = $("#studentName").val();
        var data = JSON.stringify({
            action:"student",
            data:{
                studentID:studentNum,
                studentName:studentName,
                studentCitizenID:studentID,
                studentTestID:testID
            }
        })
        window.parent.ws.send(data);
    })
})