package com.lwx.servlet.Socket;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.lwx.bean.StudentBen;
import com.lwx.bean.TestSubject;
import com.lwx.bean.TestnewsBen;
import com.lwx.mapper.ExampaperMapper;
import com.lwx.mapper.RecordMapper;
import com.lwx.mapper.achievementMapper;
import com.lwx.mapper.answerMapper;
import com.lwx.object.DataPacket;
import com.lwx.service.Testoperation;
import org.springframework.web.context.ContextLoader;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;


/**
 * @version 1.0
 * @author: xingyan
 * @date: 2021/6/19 18:36
 * @desc:
 */
@ServerEndpoint(value = "/TestSocket")
public class TestSocket {
    static Gson gson = ContextLoader.getCurrentWebApplicationContext().getBean(Gson.class);
    private Testoperation testoperation = ContextLoader.getCurrentWebApplicationContext().getBean(Testoperation.class);
    private ExampaperMapper exampaperMapper = ContextLoader.getCurrentWebApplicationContext().getBean(ExampaperMapper.class);
    private RecordMapper recordMapper = ContextLoader.getCurrentWebApplicationContext().getBean(RecordMapper.class);
    private answerMapper answerMapper = ContextLoader.getCurrentWebApplicationContext().getBean(answerMapper.class);
    private achievementMapper achievementMapper = ContextLoader.getCurrentWebApplicationContext().getBean(achievementMapper.class);
    //线程安全的静态变量，表示在线连接数
    private static volatile int onlineCount = 0;

    //用来存放每个客户端对应的WebSocketTest对象，适用于同时与多个客户端通信
    public static CopyOnWriteArraySet<TestSocket> webSocketSet = new CopyOnWriteArraySet<TestSocket>();
    //若要实现服务端与指定客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    public static ConcurrentHashMap<Session, Object> webSocketMap = new ConcurrentHashMap<Session, Object>();

    //考试房间
    public static ConcurrentHashMap<String, TestnewsBen> TestRoom = new ConcurrentHashMap<String,TestnewsBen>();

    //与某个客户端的连接会话，通过它实现定向推送(只推送给某个用户)
    private Session session;


    /**
     * 建立连接成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);  // 添加到set中
        webSocketMap.put(session,this);    // 添加到map中
        addOnlineCount();    // 添加在线人数
        System.out.println("有新连接接入，当前连接人数："  + getOnlineCount());
    }

    /**
     * 关闭连接调用的方法
     */
    @OnClose
    public void onClose(Session closeSession){
        webSocketMap.remove(session);
        webSocketSet.remove(this);
        subOnlineCount();
        String stuid = null;
        for(String key:TestRoom.keySet()){
            stuid = TestRoom.get(key).deleteSession(session);
        }
        System.out.println("有人离开，当前在线人数为：" + getOnlineCount());
    }

    /**
     *  收到客户端消息
     */
    @OnMessage
    public void onMessage(String message,Session mysession) throws Exception{
        DataPacket data = gson.fromJson(message,DataPacket.class);
        System.out.println("接收到客户端请求："+data.getAction());
        business(data);
    }
    //发送消息给对应客户端
    public void println(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    // 获取在线人数
    public static synchronized int getOnlineCount(){
        return onlineCount;
    }

    // 添加在线人+1
    public static synchronized void addOnlineCount(){
        onlineCount ++;
    }

    // 减少在线人-1
    public static synchronized void subOnlineCount(){
        onlineCount --;
    }

    public Session getSession() {
        return session;
    }
    public void business(DataPacket dataPacket){
        TestnewsBen test = null;
        List<String> list = null;
        switch (dataPacket.getAction()){
            case "identity":
                list = (ArrayList<String>)dataPacket.getData();
                if(list.get(0).equals("admin")){
                    if(TestRoom.get(list.get(1)) == null){
                        TestRoom.put(list.get(1),testoperation.Room(list.get(1),this.getSession()));
                        List<TestnewsBen> allTest = new ArrayList<TestnewsBen>();
                        for(String key:TestRoom.keySet()){
                            allTest.add(TestRoom.get(key));
                        }
                        String allTestString = JSON.toJSONString(new DataPacket("testname",allTest));
                        try {
                            for(TestSocket testSocket:webSocketSet){
                                testSocket.getSession().getBasicRemote().sendText(allTestString);

                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else{
                        TestRoom.get(list.get(1)).getAdminSoket().add(session);
                    }
                }
                TestRoom.get(list.get(1)).PrintAllStudentState();
                break;
            //开始考试
            case "start":
                test = TestRoom.get(dataPacket.getDatatext());
                if(test.getState() == TestnewsBen.NOTSTARTED){
                    test.setState(TestnewsBen.START);
                    test.open();
                    test.PrintAllStudent(new DataPacket("start",""));
                }else{
                    try {
                        this.getSession().getBasicRemote().sendText(JSON.toJSONString(new DataPacket("repeat","start")));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                test.PrintAllStudentState();
                break;
            //强制交卷
            case "submit":
                test = TestRoom.get(dataPacket.getDatatext());
                list = (ArrayList<String>)dataPacket.getData();
                String textid =dataPacket.getDatatext();
                for(String StudentID:list){
                    recordMapper.updateState("complete",StudentID,textid);
                    fenshu(textid,StudentID);
                    try {
                        Session User = test.getStudentSoketMap().get(StudentID);
                        if(User != null){
                            User.getBasicRemote().sendText(JSON.toJSONString(new DataPacket("submit",null)));
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                test.PrintAllStudentState();
                break;
            //作弊
            case "cheat":
                test = TestRoom.get(dataPacket.getDatatext());
                list = (ArrayList<String>)dataPacket.getData();
                String textid1 =dataPacket.getDatatext();
                for(String StudentID:list){
                    recordMapper.updateState("cheat",StudentID,textid1);
                    achievementMapper.insertachievement(textid1,StudentID,"0");
                    try {
                        test.getStudentSoketMap().get(StudentID).getBasicRemote().sendText(JSON.toJSONString(new DataPacket("submit",null)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                test.PrintAllStudentState();
                break;
            //违纪
            case "violation":
                test = TestRoom.get(dataPacket.getDatatext());
                list = (ArrayList<String>)dataPacket.getData();
                for(String StudentID:list){
                    Session StudenSession = test.getStudentSoketMap().get(StudentID);
                    if(StudenSession!=null){
                        try {
                            StudenSession.getBasicRemote().sendText(JSON.toJSONString(new DataPacket("violation",null)));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            //暂停考试
            case "suspend":
                test = TestRoom.get(dataPacket.getDatatext());
                test.setState(TestnewsBen.STOP);
                test.getTimer().cancel();
                test.PrintAllStudent(new DataPacket("suspend",null));
                break;
                //继续考试
            case "continue":
                test = TestRoom.get(dataPacket.getDatatext());
                test.setState(TestnewsBen.START);
                test.open();
                test.PrintAllStudent(new DataPacket("continue",null));
                break;
            case "export":
                break;
            case "testname":
                List<TestnewsBen> allTest = new ArrayList<TestnewsBen>();
                for(String key:TestRoom.keySet()){
                    allTest.add(TestRoom.get(key));
                }
                String allTestString = JSON.toJSONString(new DataPacket("testname",allTest));
                try {
                    getSession().getBasicRemote().sendText(allTestString);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "student":
                StudentBen stdent = new StudentBen();
                LinkedTreeMap Map = (LinkedTreeMap) dataPacket.getData();
                stdent.setStudentID((String)Map.get("studentID"));
                stdent.setStudentName((String)Map.get("studentName"));
                stdent.setStudentCitizenID((String)Map.get("studentCitizenID"));
                stdent.setStudentTestID((String)Map.get("studentTestID"));
                StudentBen studentBen = exampaperMapper.selectStudent(stdent);
                try {
                    session.getBasicRemote().sendText(JSON.toJSONString(new DataPacket("student",studentBen)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(studentBen != null){
                    TestnewsBen testnewsBen =TestRoom.get(studentBen.getStudentTestID());
                    testnewsBen.addStudent(studentBen.getStudentID(),session);
                }
                break;
            case "teststate":
                String testid = dataPacket.getDatatext();
                try {
                    session.getBasicRemote().sendText(JSON.toJSONString(new DataPacket("teststate",TestRoom.get(testid).getState())));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "underway":
//                String studexid = dataPacket.getDatatext();
                List<String> data =(ArrayList<String>)dataPacket.getData();
                recordMapper.updateState("underway",data.get(0),data.get(1));
                break;
            case "answers":
                String StudexID = dataPacket.getDatatext();
                String TestID = dataPacket.getDatatext2();
                if(answerMapper.selectanswer(TestID,StudexID) !=null){
                    answerMapper.updateanswer(TestID,StudexID,JSON.toJSONString((List)dataPacket.getData()));
                }else{
                    answerMapper.insertanswer(TestID,StudexID,JSON.toJSONString((List)dataPacket.getData()));
                }
                break;
            case "tijiaotext":
                String StudexIDs = dataPacket.getDatatext();
                String TestIDs = dataPacket.getDatatext2();
                int fraction = fenshu(TestIDs,StudexIDs);
                recordMapper.updateState("complete",StudexIDs,TestIDs);
                TestRoom.get(TestIDs).deleteSession(session);
                TestRoom.get(TestIDs).PrintAllStudentState();
                try {
                    session.getBasicRemote().sendText(JSON.toJSONString(new DataPacket("tijiaotext",null)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "chengji":
                String StudexId = dataPacket.getDatatext();
                String TestId = dataPacket.getDatatext2();
                try {
                    session.getBasicRemote().sendText(JSON.toJSONString(new DataPacket("chengji",String.valueOf(achievementMapper.selectachievement(TestId,StudexId)))));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }

    }
    public void jieshutest(String StudentID,String textid,TestnewsBen test){
        recordMapper.updateState("complete",StudentID,textid);
        fenshu(textid,StudentID);
        try {
            Session User = test.getStudentSoketMap().get(StudentID);
            if(User != null){
                User.getBasicRemote().sendText(JSON.toJSONString(new DataPacket("submit",null)));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public int fenshu(String TestIDs,String StudexIDs){
        int cj = 0;
        String answer = answerMapper.selectanswer(TestIDs,StudexIDs);
        if(answer!=null){
            List<HashMap> liste = JSON.parseArray(answer, HashMap.class);
            for(java.util.Map<String,List<String>> map:liste){
                for(String key:map.keySet()){
                    String daans = "";
                    for(String dann:map.get(key)){
                        if(!daans.isEmpty()){
                            daans = daans+",";
                        }
                        daans = daans+dann;
                    }
                    for(TestSubject subject:TestRoom.get(TestIDs).getTestSubject()){
//                            System.out.println(subject.getTestSubjectAnswer()+":"+daans+":"+subject.getTestSubjectAnswer().equals(daans));
                        if(subject.getTestSubjectNum().equals(key)&&subject.getTestSubjectAnswer().equals(daans)){
                            cj +=subject.getFraction();
                            break;
                        }
                    }

                }
            }
            achievementMapper.insertachievement(TestIDs,StudexIDs,String.valueOf(cj));
        }
        return cj;
    }
}
