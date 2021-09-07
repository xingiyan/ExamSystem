package com.lwx.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @author: xingyan
 * @date: 2021/6/13 18:48
 * @desc:
 */
public class TestSubject {
    //多选题
    public final String TESTMULTIPLECHOICEQUESTIONS = "多选题";
    //单选题
    public final String TESTSINGLECHOICEQUESTIONS = "单选题";
    //填空题
    public final String COMPLETION = "Completion";
    //题号
    private String TestSubjectNum = null;
    //题目类型
    private String TestSubjectCLass = null;
    //题目问题
    private String TestSubjectTitle = null;
    //题目可选答案
    private Map<String,String> TestSubjectOptionalAnswer = new HashMap<String,String>();
    //题目答案
    private String TestSubjectAnswer = null;
    //题目分数
    private int Fraction = 0;

    public TestSubject(String testSubjectNum, String testSubjectCLass, String testSubjectTitle, Map<String,String> testSubjectOptionalAnswer, String testSubjectAnswer, int fraction) {
        TestSubjectNum = testSubjectNum;
        TestSubjectCLass = testSubjectCLass;
        TestSubjectTitle = testSubjectTitle;
        TestSubjectOptionalAnswer = testSubjectOptionalAnswer;
        TestSubjectAnswer = testSubjectAnswer;
        Fraction = fraction;
    }

    @Override
    public String toString() {
        return "TestSubject{" +
                "TestSubjectNum='" + TestSubjectNum + '\'' +
                ", TestSubjectCLass='" + TestSubjectCLass + '\'' +
                ", TestSubjectTitle='" + TestSubjectTitle + '\'' +
                ", TestSubjectOptionalAnswer=" + TestSubjectOptionalAnswer +
                ", TestSubjectAnswer=" + TestSubjectAnswer +
                ", Fraction=" + Fraction +
                '}';
    }

    public int getFraction() {
        return Fraction;
    }

    public void setFraction(int fraction) {
        Fraction = fraction;
    }

    public TestSubject() {
    }

    public String getTestSubjectNum() {
        return TestSubjectNum;
    }

    public void setTestSubjectNum(String testSubjectNum) {
        TestSubjectNum = testSubjectNum;
    }

    public String getTestSubjectCLass() {
        return TestSubjectCLass;
    }

    public void setTestSubjectCLass(String testSubjectCLass) {
        TestSubjectCLass = testSubjectCLass;
    }

    public String getTestSubjectTitle() {
        return TestSubjectTitle;
    }

    public void setTestSubjectTitle(String testSubjectTitle) {
        TestSubjectTitle = testSubjectTitle;
    }

    public Map<String,String> getTestSubjectOptionalAnswer() {
        return TestSubjectOptionalAnswer;
    }

    public void setTestSubjectOptionalAnswer(Map<String,String> testSubjectOptionalAnswer) {
        TestSubjectOptionalAnswer = testSubjectOptionalAnswer;
    }

    public String getTestSubjectAnswer() {
        return TestSubjectAnswer;
    }

    public void setTestSubjectAnswer(String testSubjectAnswer) {
        TestSubjectAnswer = testSubjectAnswer;
    }
}
