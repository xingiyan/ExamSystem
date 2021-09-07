package com.lwx.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.google.gson.annotations.Expose;
import com.lwx.mapper.RecordMapper;
import com.lwx.object.DataPacket;
import com.lwx.servlet.Socket.TestSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ContextLoader;

import javax.websocket.Session;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @version 1.0
 * @author: xingyan
 * @date: 2021/6/13 4:06
 * @desc:  考试信息
 */
public class TestnewsBen {

    //考试编号
    private String testID = null;
    //考试试卷名称
    private String testName = null;
    //考试时间
    private String testtime = null;
    //剩余时间
    private long testSurplusTime = 0;
    //考试类型
    private String testClass = null;
    //鉴定工种
    private String testtype = null;
    //鉴定机构
    private String testCompany = null;
    //鉴定等级
    private String testGrade = null;
    //及格分数
    private String testpass = null;
    //考生
    private List<StudentBen> studentBens = null;
    //多选题规则
    private String testMultipleChoiceQuestions = null;
    //单选题规则
    private String testSinglechoiceQuestions = null;
    //题目
    private List<TestSubject> testSubject = new ArrayList<TestSubject>();
    @JSONField(serialize = false)
    private transient Timer timer;
    //考生连接soket
    //方便精准通讯
    @JSONField(serialize = false)
    private transient ConcurrentHashMap<String, Session> studentSoketMap = new ConcurrentHashMap<String,Session>();
    @JSONField(serialize = false)
    private transient CopyOnWriteArraySet<Session> studentSoketList = new CopyOnWriteArraySet<Session>();
    //管理端soket
    @JSONField(serialize = false)
    private transient List<Session> adminSoket = new ArrayList<Session>();
    //考试状态
    @JSONField(serialize = false)
    private transient RecordMapper recordMapper = ContextLoader.getCurrentWebApplicationContext().getBean(RecordMapper.class);
    @Autowired
    @JSONField(serialize = false)
    private TestSocket testSocket;

    private int state = NOTSTARTED;

    //未开始考试
    public static final int NOTSTARTED = 0;
    //正在考试
    public static final int START = 1;
    //正在考试
    public static final int STOP = 2;
    //向所有学生端广播消息
    public synchronized void PrintAllStudent(DataPacket test) {
        String testString = JSON.toJSONString(test);
        for(Session session:studentSoketList){
            try {
                session.getBasicRemote().sendText(testString);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void PrintAllTime(){
        DataPacket Time = new DataPacket("time",String.valueOf(testSurplusTime));
        PrintAllStudent(Time);
        printAllAdmin(Time);
    }
    public void open(){
        setTimer(new Timer());
        TestnewsBen testnewsBen = this;
        getTimer().schedule(new TimerTask() {
            @Override
            public void run() {
                if(getTestSurplusTime() != 0){
                    setTestSurplusTime(getTestSurplusTime()-1000);
                    PrintAllTime();
                }else{
                    for(StudentBen studentBen : studentBens){
                        testSocket.jieshutest(studentBen.getStudentID(),studentBen.getStudentTestID(),testnewsBen);
                    }
                    getTimer().cancel();
                }
            }
        },0,1000);
    }
    //向所有的管理端广播消息
    public synchronized void printAllAdmin(DataPacket data){
        for(Session session:adminSoket){
            try {
                session.getBasicRemote().sendText(JSON.toJSONString(data));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //向管理端发送所有已连接的学生信息
    public void AllStudentID(){

    }
    public void addStudent(String SID,Session session){
        studentSoketMap.put(SID,session);
        studentSoketList.add(session);
        PrintAllStudentState();
    }
    public void PrintAllStudentState(){
        Map<String,List<String>> AllStudentState = new HashMap<String,List<String>>();
        List<String> Nullstate = new ArrayList<String>(); //未登录与无异常状态
        List<String> underways = new ArrayList<String>(); //考试中
        List<String> completes = new ArrayList<String>();//已完成考试
        List<String> cheats = new ArrayList<String>();   //作弊
        List<StudentBen> AllStudent = recordMapper.selectAllState(testID);
        for(StudentBen studentBen:AllStudent){
            for(String studid:studentSoketMap.keySet()){
                if(studid.equals(studentBen.getStudentID())
                        ||(!studentBen.getState().equals("complete"))
                        ||(!studentBen.getState().equals("cheat"))){
                    underways.add(studid);
                }
            }
            if(studentBen.getState().equals("complete")){
                completes.add(studentBen.getStudentID());
            }
            if(studentBen.getState().equals("cheat")){
                cheats.add(studentBen.getStudentID());
            }
        }
        for(StudentBen studentBen:AllStudent){
            boolean t = true;
            for(String unde:underways){
                if(studentBen.getStudentID().equals(unde)){
                    t=false;
                }
            }
            for(String comp:completes){
                if(studentBen.getStudentID().equals(comp)){
                    t=false;
                }
            }
            for(String che:cheats){
                if(studentBen.getStudentID().equals(che)){
                    t=false;
                }
            }
            if(t){
                Nullstate.add(studentBen.getStudentID());
            }
        }
        AllStudentState.put("underway",underways);
        AllStudentState.put("complete",completes);
        AllStudentState.put("cheat",cheats);
        AllStudentState.put("Nullstate",Nullstate);
        printAllAdmin(new DataPacket("studenstate",AllStudentState));
    }
    public String deleteSession(Session session){
        String studentid = null;
        for(String kay:studentSoketMap.keySet()){
            if(studentSoketMap.get(kay) == session){
                studentid = kay;
                studentSoketMap.remove(kay);
                break;
            }
        }
        studentSoketList.remove(session);
        adminSoket.remove(session);
        if(studentid != null){
            String state = recordMapper.selectstate(studentid,testID);
            if(state.equals("underway")){
                recordMapper.updateState("normal",studentid,testID);
            }
        }
        PrintAllStudentState();
        return studentid;
    }
    public TestnewsBen() {
    }

    public long getTestSurplusTime() {
        return testSurplusTime;
    }

    public void setTestSurplusTime(long testSurplusTime) {
        this.testSurplusTime = testSurplusTime;
    }

    public TestnewsBen(String testID, String testName, String testtime, String testClass, String testtype, String testCompany, String testGrade, String testpass, List<StudentBen> studentBens, String testMultipleChoiceQuestions, String testSinglechoiceQuestions, List<TestSubject> testSubject, ConcurrentHashMap<String, Session> studentSoketMap, CopyOnWriteArraySet studentSoketList, ArrayList adminSoket, int state) {
        this.testID = testID;
        this.testName = testName;
        this.testtime = testtime;
        this.testClass = testClass;
        this.testtype = testtype;
        this.testCompany = testCompany;
        this.testGrade = testGrade;
        this.testpass = testpass;
        this.studentBens = studentBens;
        this.testMultipleChoiceQuestions = testMultipleChoiceQuestions;
        this.testSinglechoiceQuestions = testSinglechoiceQuestions;
        this.testSubject = testSubject;
        this.studentSoketMap = studentSoketMap;
        this.studentSoketList = studentSoketList;
        this.adminSoket = adminSoket;
        this.state = state;
    }

    public ConcurrentHashMap<String, Session> getStudentSoketMap() {
        return studentSoketMap;
    }

    public void setStudentSoketMap(ConcurrentHashMap<String, Session> studentSoketMap) {
        this.studentSoketMap = studentSoketMap;
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    @Override
    public String toString() {
        return "TestnewsBen{" +
                "testID='" + testID + '\'' +
                ", testName='" + testName + '\'' +
                ", testtime='" + testtime + '\'' +
                ", testClass='" + testClass + '\'' +
                ", testtype='" + testtype + '\'' +
                ", testCompany='" + testCompany + '\'' +
                ", testGrade='" + testGrade + '\'' +
                ", testpass='" + testpass + '\'' +
                ", studentBens=" + studentBens +
                ", testMultipleChoiceQuestions='" + testMultipleChoiceQuestions + '\'' +
                ", testSinglechoiceQuestions='" + testSinglechoiceQuestions + '\'' +
                ", testSubject=" + testSubject +
                ", studentSoketMap=" + studentSoketMap +
                ", studentSoketList=" + studentSoketList +
                ", adminSoket=" + adminSoket +
                ", state=" + state +
                '}';
    }

    public CopyOnWriteArraySet<Session> getStudentSoketList() {
        return studentSoketList;
    }

    public void setStudentSoketList(CopyOnWriteArraySet<Session> studentSoketList) {
        this.studentSoketList = studentSoketList;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }


    public List<Session> getAdminSoket() {
        return adminSoket;
    }

    public void setAdminSoket(ArrayList<Session> adminSoket) {
        this.adminSoket = adminSoket;
    }

    public List<StudentBen> getStudentBens() {
        return studentBens;
    }

    public void setStudentBens(List<StudentBen> studentBens) {
        this.studentBens = studentBens;
    }

    public String getTestID() {
        return testID;
    }


    public String getTestMultipleChoiceQuestions() {
        return testMultipleChoiceQuestions;
    }

    public void setTestMultipleChoiceQuestions(String testMultipleChoiceQuestions) {
        this.testMultipleChoiceQuestions = testMultipleChoiceQuestions;
    }

    public String getTestSinglechoiceQuestions() {
        return testSinglechoiceQuestions;
    }

    public void setTestSinglechoiceQuestions(String testSinglechoiceQuestions) {
        this.testSinglechoiceQuestions = testSinglechoiceQuestions;
    }



    public List<TestSubject> getTestSubject() {
        return testSubject;
    }

    public void setTestSubject(List<TestSubject> testSubject) {
        this.testSubject = testSubject;
    }

    public void setTestID(String testID) {
        this.testID = testID;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTesttime() {
        return testtime;
    }

    public void setTesttime(String testtime) {
        this.testtime = testtime;
    }

    public String getTestClass() {
        return testClass;
    }

    public void setTestClass(String testClass) {
        this.testClass = testClass;
    }

    public String getTesttype() {
        return testtype;
    }

    public void setTesttype(String testtype) {
        this.testtype = testtype;
    }

    public String getTestCompany() {
        return testCompany;
    }

    public void setTestCompany(String testCompany) {
        this.testCompany = testCompany;
    }

    public String getTestGrade() {
        return testGrade;
    }

    public void setTestGrade(String testGrade) {
        this.testGrade = testGrade;
    }

    public String getTestpass() {
        return testpass;
    }

    public void setTestpass(String testpass) {
        this.testpass = testpass;
    }

}
