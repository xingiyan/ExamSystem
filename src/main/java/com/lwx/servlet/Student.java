package com.lwx.servlet;

import com.alibaba.fastjson.JSON;
import com.lwx.Tool.CreateVerificationCode;
import com.lwx.Tool.CreateVerificationCodeImage;
import com.lwx.bean.StudentBen;
import com.lwx.bean.TestnewsBen;
import com.lwx.mapper.ExampaperMapper;
import com.lwx.mapper.TestMapper;
import com.lwx.mapper.answerMapper;
import com.lwx.object.DataPacket;
import com.lwx.service.impl.FileServiceFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * @version 1.0
 * @author: xingyan
 * @date: 2021/6/9 18:31
 * @desc:
 */
@WebServlet("/Student")
public class Student extends HttpServlet {
    @Autowired
    private answerMapper answerMapper;
    @Autowired
    private ExampaperMapper exampaperMapper;
    @Autowired
    private TestMapper testMapper;
    @Autowired
    private FileServiceFile fileServiceFile;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Business(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }
    private void Business(HttpServletRequest req, HttpServletResponse resp){
        try {
            req.setCharacterEncoding("utf-8");
            resp.setCharacterEncoding("utf-8");
            PrintWriter PW;  //提取流
            String request =req.getParameter("request");
            System.out.println("request:"+request);
            switch (request){
                case "GetTest":
                    String StudentID = req.getParameter("studentID");
                    StudentBen studentBen = exampaperMapper.Student(StudentID);
                    req.getSession().setAttribute("studentBen",studentBen);
                    if(studentBen.getState().equals("normal")){
                        String TestID  =req.getParameter("TestID");
                        String TestName = testMapper.queryTest(TestID);
                        req.getSession().setAttribute("test",fileServiceFile.GetTest(TestName));
                    }
                    PW = resp.getWriter();
                    PW.println(JSON.toJSONString(new DataPacket("GetTest",studentBen.getState())));
                    break;
                case "getanswer":
                    System.out.println("进入getanswer");
                    String tid = req.getParameter("TestID");
                    String eid = req.getParameter("StudexID");
                    PW = resp.getWriter();
                    PW.println(answerMapper.selectanswer(tid,eid));
                    break;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
