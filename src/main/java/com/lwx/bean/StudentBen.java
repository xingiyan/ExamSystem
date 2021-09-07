package com.lwx.bean;


/**
 * @version 1.0
 * @author: xingyan
 * @date: 2021/6/14 3:13
 * @desc:
 */
public class StudentBen {

    //考生ID
    private String studentID = null;
    //考生名字
    private String studentName = null;
    //考生身份证
    private String studentCitizenID = null;
    //考生试卷ID
    private String studentTestID = null;
    //考生当前状态
    private String state = null;
    //成绩
    private String ment = null;

    @Override
    public String toString() {
        return "StudentBen{" +
                "studentID='" + studentID + '\'' +
                ", studentName='" + studentName + '\'' +
                ", studentCitizenID='" + studentCitizenID + '\'' +
                ", studentTestID='" + studentTestID + '\'' +
                ", state='" + state + '\'' +
                '}';
    }

    public StudentBen(String studentID, String studentName, String studentCitizenID, String studentTestID) {
        this.studentID = studentID;
        this.studentName = studentName;
        this.studentCitizenID = studentCitizenID;
        this.studentTestID = studentTestID;
    }

    public StudentBen(String studentID, String studentName, String studentCitizenID, String studentTestID, String state) {
        this.studentID = studentID;
        this.studentName = studentName;
        this.studentCitizenID = studentCitizenID;
        this.studentTestID = studentTestID;
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public StudentBen() {
    }

    public String getMent() {
        return ment;
    }

    public void setMent(String ment) {
        this.ment = ment;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentCitizenID() {
        return studentCitizenID;
    }

    public void setStudentCitizenID(String studentCitizenID) {
        this.studentCitizenID = studentCitizenID;
    }

    public String getStudentTestID() {
        return studentTestID;
    }

    public void setStudentTestID(String studentTestID) {
        this.studentTestID = studentTestID;
    }
}
