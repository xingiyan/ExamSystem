package com.lwx.servlet;

import com.alibaba.fastjson.JSON;
import com.lwx.Tool.CreateVerificationCode;
import com.lwx.Tool.CreateVerificationCodeImage;
import com.lwx.Tool.ExcelUtils;
import com.lwx.bean.StudentBen;
import com.lwx.mapper.ExampaperMapper;
import com.lwx.object.DataPacket;
import com.lwx.service.impl.FileServiceFile;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @author: xingyan
 * @date: 2021/6/9 18:31
 * @desc:
 */
@WebServlet("/DocumentControl")
public class DocumentControl extends HttpServlet {
    @Autowired
    private FileServiceFile fileServiceFile;
    @Autowired
    private ExampaperMapper exampaperMapper;
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
                case "VerificationCode":
                    //        验证码
                    String code = CreateVerificationCode.getSecurityCode();
                    req.getSession().setAttribute("code",code);
                    CreateVerificationCodeImage createVerificationCodeImage = new CreateVerificationCodeImage(code);
                    BufferedImage image = createVerificationCodeImage.createImage();
                    //        发给login
                    resp.setContentType("img/jpeg");
                    ImageIO.write(image, "JPEG", resp.getOutputStream());
                    break;
                case "file":
                    String filename =req.getParameter("filename");
                    String FileName = filename.split("\\.")[0];
                    String FileClass = filename.split("\\.")[1];
                    String File = req.getParameter("file");
                    String Fileonly = req.getParameter("Fileonly");
                    PW = resp.getWriter();
                    PW.println(JSON.toJSONString(new DataPacket("file",fileServiceFile.File(FileName,File,FileClass,Fileonly.equals("true")))));
                    PW.flush();
                    break;
                case "Excel":
                    String TID = req.getParameter("TID");
                    List<StudentBen> studentBens = exampaperMapper.selectStudenment(TID);
                    List<String> title = new ArrayList<String>();
                    title.add("学生ID");
                    title.add("学生名字");
                    title.add("学生成绩");
                    //处理文件名
                    List<List<String>> studen = new ArrayList<>();
                    for(StudentBen studentBen:studentBens){
                        List<String> list = new ArrayList<String>();
                        list.add(studentBen.getStudentID());
                        list.add(studentBen.getStudentName());
                        list.add(studentBen.getMent());
                        studen.add(list);
                    }
                    HSSFWorkbook hwk = ExcelUtils.getHSSFWorkbook(TID,title,studen,null);
//                    String realname = fileName.substring(fileName.indexOf("_")+1);
                    //设置响应头，控制浏览器下载该文件
                    resp.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(TID+".xls", "UTF-8"));
                    //读取要下载的文件，保存到文件输入流
                    //创建输出流
                    OutputStream out = resp.getOutputStream();
                    hwk.write(out);
                    out.flush();
                    //创建缓冲区
//                    byte buffer[] = new byte[1024];
//                    int len = 0;
//                    //循环将输入流中的内容读取到缓冲区当中
//                    while((len=in.read(buffer))>0){
//                        //输出缓冲区的内容到浏览器，实现文件下载
//                        out.write(buffer, 0, len);
//                    }
//                    //关闭文件输入流
//                    in.close();
//                    //关闭输出流
//                    out.close();
                    break;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
