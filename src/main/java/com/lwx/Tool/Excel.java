package com.lwx.Tool;

import com.lwx.bean.StudentBen;
import com.lwx.bean.TestSubject;
import com.lwx.bean.TestnewsBen;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @author: xingyan
 * @date: 2021/6/13 16:49
 * @desc:
 */
public class Excel {
    public static List<String> analysis(File excel) {
        Workbook wb = null;
        FileInputStream fis = null;
        try {
            if (excel != null||(excel.isFile() && excel.exists())) {   //判断文件是否存在
                String[] split = excel.getName().split("\\.");  //.是特殊字符，需要转义！！！！！

                //根据文件后缀（xls/xlsx）进行判断
                if ("xls".equals(split[1])) {
                    fis = new FileInputStream(excel);   //文件流对象
                    wb = new HSSFWorkbook(fis);
                } else if ("xlsx".equals(split[1])) {
                    wb = new XSSFWorkbook(excel);
                } else {
                    System.out.println("文件类型错误!");
                    return null;
                }

                //开始解析
                Sheet sheet = wb.getSheetAt(0);     //读取sheet 0

                int firstRowIndex = sheet.getFirstRowNum();
                int lastRowIndex = sheet.getLastRowNum();

                for (int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {   //遍历行
                    Row row = sheet.getRow(rIndex);
                    if (row != null) {
                        int firstCellIndex = row.getFirstCellNum();
                        int lastCellIndex = row.getLastCellNum();
                        for (int cIndex = firstCellIndex; cIndex < lastCellIndex; cIndex++) {   //遍历列
                            Cell cell = row.getCell(cIndex);
                            if (cell != null) {
                                System.out.println(cell.toString());
                            }
                        }
                    }
                }
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(wb!=null){
                    wb.close();
                }
                if(fis!=null){
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    public static TestnewsBen GetTest(File excel,File ExamPaper){
        TestnewsBen testnewsBen = new TestnewsBen();
        FileInputStream fis = null;
        Workbook wb = null;
        try {
            if (excel.isFile() && excel.exists()) {   //判断文件是否存在
                String[] split = excel.getName().split("\\.");  //.是特殊字符，需要转义！！！！！
                //根据文件后缀（xls/xlsx）进行判断
                if ("xls".equals(split[1])) {
                    fis = new FileInputStream(excel);   //文件流对象
                    wb = new HSSFWorkbook(fis);
                } else if ("xlsx".equals(split[1])) {
                    wb = new XSSFWorkbook(excel);
                } else {
                    System.out.println("文件类型错误!");
                    return null;
                }
                Sheet sheet = wb.getSheetAt(0);
                Row row1 = sheet.getRow(0);
                Row row2 = sheet.getRow(1);
                for(int i = 0;i<10;i++){
                    Cell cell = row1.getCell(i);
                    if(cell != null){
                        switch (cell.toString()){
                            case "试卷编号":
                                testnewsBen.setTestID(row2.getCell(i).toString());
                                break;
                            case "考试名称":
                                testnewsBen.setTestName(row2.getCell(i).toString());
                                break;
                            case "考试时间":
                                testnewsBen.setTesttime(row2.getCell(i).toString());
                                break;
                            case "考试类型":
                                testnewsBen.setTestClass(row2.getCell(i).toString());
                                break;
                            case "鉴定工种":
                                testnewsBen.setTesttype(row2.getCell(i).toString());
                                break;
                            case "鉴定机构":
                                testnewsBen.setTestCompany(row2.getCell(i).toString());
                                break;
                            case "鉴定等级":
                                testnewsBen.setTestGrade(row2.getCell(i).toString());
                                break;
                            case "及格分数":
                                testnewsBen.setTestpass(row2.getCell(i).toString());
                                break;
                            case "多选题":
                                testnewsBen.setTestMultipleChoiceQuestions(row2.getCell(i).toString());
                                break;
                            case "单选题":
                                testnewsBen.setTestSinglechoiceQuestions(row2.getCell(i).toString());
                                break;
                        }
                    }
                }
                Row row = sheet.getRow(2);
                int lastRowIndex = row.getLastCellNum();
                //题目类型
                int TestSubjectClassnum = 0;
                //题号
                int TestSubjectNums = 0;
                //题目问题
                int TestSubjectTitlenum = 0;
                //题目可选答案
                Map<String,Integer> TestSubjectOptionalAnswernum = new HashMap<String,Integer>();
                //题目答案
                int TestSubjectAnswernum = 0;
                //题目分值
                int Fractionnum = 0;
                for(int numindex = 0;numindex<lastRowIndex;numindex++){
                    Cell cell = row.getCell(numindex);
                    if(cell != null){
                        switch (cell.toString()){
                            case "题型":
                                TestSubjectClassnum = numindex;
                                break;
                            case "题号":
                                TestSubjectNums = numindex;
                                break;
                            case "题干":
                                TestSubjectTitlenum = numindex;
                                break;
                            case "A":
                                TestSubjectOptionalAnswernum.put("A",numindex);
                                break;
                            case "B":
                                TestSubjectOptionalAnswernum.put("B",numindex);
                                break;
                            case "C":
                                TestSubjectOptionalAnswernum.put("C",numindex);
                                break;
                            case "D":
                                TestSubjectOptionalAnswernum.put("D",numindex);
                                break;
                            case "答案":
                                TestSubjectAnswernum = numindex;
                                break;
                            case "分值":
                                Fractionnum = numindex;
                                break;
                        }
                    }
                }
                int firstRowIndex = 3;
                int lastCellIndex = sheet.getLastRowNum();
                for (int rIndex = firstRowIndex; rIndex <= lastCellIndex; rIndex++) {   //遍历行
                    Row row3= sheet.getRow(rIndex);
                    TestSubject testSubject = new TestSubject();
                    testSubject.setTestSubjectNum(row3.getCell(TestSubjectNums).toString());
                    testSubject.setTestSubjectCLass(row3.getCell(TestSubjectClassnum).toString());
                    testSubject.setTestSubjectTitle(row3.getCell(TestSubjectTitlenum).toString());
                    testSubject.setFraction(Integer.valueOf(row3.getCell(Fractionnum).toString()));
                    testSubject.setTestSubjectAnswer(row3.getCell(TestSubjectAnswernum).toString());
                    HashMap hashMap = new HashMap<String,String>();
                    hashMap.put("A",row3.getCell(TestSubjectOptionalAnswernum.get("A")).toString());
                    hashMap.put("B",row3.getCell(TestSubjectOptionalAnswernum.get("B")).toString());
                    hashMap.put("C",row3.getCell(TestSubjectOptionalAnswernum.get("C")).toString());
                    hashMap.put("D",row3.getCell(TestSubjectOptionalAnswernum.get("D")).toString());
                    testSubject.setTestSubjectOptionalAnswer(hashMap);
                    testnewsBen.getTestSubject().add(testSubject);
                }
            }
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

            try {
                if(wb != null){
                    wb.close();
                }
                if(fis != null){
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            if (ExamPaper != null || (ExamPaper.isFile() && ExamPaper.exists())) {   //判断文件是否存在
                String[] split = ExamPaper.getName().split("\\.");  //.是特殊字符，需要转义！！！！！
                FileInputStream fis2 = new FileInputStream(ExamPaper);
                Sheet sheet = null;
                Row row1 = null;
                int lastCellIndex = 0;
                int studentIDNum = 0;
                int studentNameNum = 0;
                int studentCitizenIDNum = 0;
                int studentTestIDNum = 0;
                List<StudentBen> studentBens = new ArrayList<StudentBen>();
                //根据文件后缀（xls/xlsx）进行判断
                if ("xls".equals(split[1])) {
                    wb = new HSSFWorkbook(fis2);
                } else if ("xlsx".equals(split[1])) {
                    wb = new XSSFWorkbook(fis2);
                } else {
                    System.out.println("文件类型错误!");
                    return null;
                }
                sheet = wb.getSheetAt(0);
                row1 = sheet.getRow(0);
                int lastRowIndex = row1.getLastCellNum();
                for(int i = 0;i<lastRowIndex;i++){
                    switch (row1.getCell(i).toString()){
                        case "EXAMINATION_CARD_NUMBER":
                            studentIDNum = i;
                            break;
                        case "STUDENT_NAME":
                            studentNameNum = i;
                            break;
                        case "ID_CARD_NUMBER":
                            studentCitizenIDNum = i;
                            break;
                        case "EXAM_NUMBER":
                            studentTestIDNum = i;
                            break;
                    }
                }
                lastCellIndex = sheet.getLastRowNum();
                for(int i=1;i<=lastCellIndex;i++){
                    Row row = sheet.getRow(i);
                    StudentBen studentBen = new StudentBen();
                    studentBen.setStudentID(row.getCell(studentIDNum).toString());
                    studentBen.setStudentName(row.getCell(studentNameNum).toString());
                    studentBen.setStudentCitizenID(row.getCell(studentCitizenIDNum).toString());
                    studentBen.setStudentTestID(row.getCell(studentTestIDNum).toString());
                    studentBens.add(studentBen);
                }
                testnewsBen.setStudentBens(studentBens);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(wb!=null){
                    wb.close();
                }
                if(fis!=null){
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return testnewsBen;
    }
}
