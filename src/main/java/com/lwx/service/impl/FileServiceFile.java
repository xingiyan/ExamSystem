package com.lwx.service.impl;

import com.alibaba.fastjson.JSON;
import com.lwx.Tool.Excel;
import com.lwx.Tool.UnZipUtils;
import com.lwx.bean.StudentBen;
import com.lwx.bean.TestnewsBen;
import com.lwx.mapper.ExampaperMapper;
import com.lwx.mapper.RecordMapper;
import com.lwx.mapper.TestMapper;
import com.lwx.object.Admin;
import com.lwx.object.AdminList;
import com.lwx.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;
import java.util.Properties;

/**
 * @version 1.0
 * @author: xingyan
 * @date: 2021/6/7 14:08
 * @desc:
 */
@Service
public class FileServiceFile implements FileService {
    @Autowired
    private TestMapper testMapper;
    @Autowired
    private ExampaperMapper exampaperMapper;
    @Autowired
    private RecordMapper recordMapper;
    public String fileString;
    public String testpaper;
    public FileServiceFile(){
        Properties properties = new Properties();
        BufferedReader bufferedReader = null;
        System.out.println("开始读取文件存放路径");
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream
                    (AdminList.class.getResource("/configure/File.properties").getFile()), "UTF-8"));
            properties.load(bufferedReader);
            fileString = properties.getProperty("file");
            testpaper = properties.getProperty("testpaper");
            String nodepath = this.getClass().getClassLoader().getResource("/").getPath();
            String filePath = nodepath.substring(1, nodepath.length() - 37);
            if(fileString.isEmpty()){
                fileString = filePath+"/file/";
                System.out.println("读取到配置文件file属性为空，启用默认配置");
            }
            System.out.println("文件上传路径为："+fileString);
            if(testpaper.isEmpty()){
                testpaper = filePath+"/testpaper/";
                System.out.println("读取到配置文件testpaper属性为空，启用默认配置");
            }
            System.out.println("文件解压路径为："+testpaper);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("文件存储路径读取完毕");
    }
    @Override
    public boolean File(String Name,String File,String FileClass,boolean fileonly) {
        DataOutputStream DOS =null;
        try {
            if(FileClass.equals("zip")){
                if(fileonly){
                    File file = new File(fileString+Name);
                    if(file.exists()){
                        for(String filezip :file.list()){
                            new File(fileString+Name+"\\"+filezip).delete();
                        }
                        file.delete();
                    }
                    file.mkdir();
                    new File(testpaper+Name).mkdir();
                }
                DOS = new DataOutputStream(new FileOutputStream(fileString+Name+"\\"+Name+"."+FileClass,true));
                DOS.writeBytes(File);
                DOS.flush();
                return true;
            }else{
                return false;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(DOS != null){
                    DOS.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public String UnZipUtils(String Name,String password) {
        return UnZipUtils.unZip(fileString+Name.split("\\.")[0]+"\\"+Name,testpaper+Name.split("\\.")[0]+"\\",password==null?"":password);
//                == UnZipUtils.OperationSuccessful){
//            return Excel.GetTest(new File("E:\\test\\testpaper\\"+Name.split("\\.")[0]+"\\"+Name+"\\Test.xls"),new File("E:\\test\\testpaper\\"+Name.split("\\.")[0]+"\\"+Name+"\\ExamPaper.xls"));
//        }
//        return null;
    }

    @Override
    public TestnewsBen GetTest(String Name) {
        TestnewsBen testnewsBen = Excel.GetTest(new File(testpaper+Name.split("\\.")[0]+"\\"+"Test.xls"),new File(testpaper+Name.split("\\.")[0]+"\\"+"ExamPaper.xls"));
        if(testMapper.queryTest(testnewsBen.getTestID()) == null){
            testMapper.newtest(testnewsBen,Name);
            for(StudentBen studentBen:testnewsBen.getStudentBens()){
                exampaperMapper.newstudent(studentBen);
                recordMapper.newrecord(testnewsBen.getTestID(),studentBen.getStudentID());
            }
        }
        return testnewsBen;
    }
}
