package com.lwx.service.impl;

import com.lwx.Tool.Excel;
import com.lwx.bean.TestnewsBen;
import com.lwx.mapper.TestMapper;
import com.lwx.service.FileService;
import com.lwx.service.Testoperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.Session;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @version 1.0
 * @author: xingyan
 * @date: 2021/6/19 22:26
 * @desc:
 */
@Service
public class TestOperation implements Testoperation {
    @Autowired
    TestMapper testMapper;

    @Override
    public TestnewsBen Room(String TestID,Session session) {
        String Name = testMapper.queryTest(TestID);
        TestnewsBen testnewsBen = Excel.GetTest(new File("E:\\test\\testpaper\\"+Name.split("\\.")[0]+"\\"+"Test.xls"),new File("E:\\test\\testpaper\\"+Name.split("\\.")[0]+"\\"+"ExamPaper.xls"));
        testnewsBen.getAdminSoket().add(session);
        String timeString = testnewsBen.getTesttime();
        if(timeString.endsWith("小时")){
            testnewsBen.setTestSurplusTime(Long.valueOf(timeString.split("小时")[0])*60*60*1000);
        }else if(timeString.endsWith("分钟")){
            testnewsBen.setTestSurplusTime(Long.valueOf(timeString.split("分钟")[0])*60*1000);
        }
        return testnewsBen;
    }
}
