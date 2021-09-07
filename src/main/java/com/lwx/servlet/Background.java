package com.lwx.servlet;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.lwx.Tool.UnZipUtils;
import com.lwx.bean.TestnewsBen;
import com.lwx.mapper.TestMapper;
import com.lwx.object.Admin;
import com.lwx.object.AdminList;
import com.lwx.object.DataPacket;
import com.lwx.service.SignService;
import com.lwx.service.impl.FileServiceFile;
import com.lwx.service.impl.SignServiceimpl;
import net.lingala.zip4j.unzip.UnzipUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @version 1.0
 * @author: xingyan
 * @date: 2021/6/4 19:01
 * @desc:
 */

@WebServlet("/Background")
public class Background extends HttpServlet {
    private String state = "暂停";
    @Autowired
    private Gson gson;
    @Autowired
    private SignServiceimpl signServiceimpl;
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
            PrintWriter PW = resp.getWriter();  //提取流
            String request =req.getParameter("request");
            System.out.println("request=："+request);
            switch (request){
                case "Sign":
                    String acc = req.getParameter("acc");
                    String pwd = req.getParameter("pwd");
                    System.out.println(acc+":::"+pwd);
                    String Date = JSON.toJSONString(new DataPacket("Sign",signServiceimpl.Sign(acc,pwd,req)));
                    System.out.println(Date);
                    PW.println(Date);
                    PW.flush();
                    break;
                case "code":
                    String code = ((String) req.getSession().getAttribute("code")).toUpperCase();
                    String code1 = (req.getParameter("code")).toUpperCase();
                    PW.println(JSON.toJSONString(new DataPacket("code",code.equals(code1))));
                    PW.flush();
                    break;
                case "time":
                    String time = String.valueOf(System.currentTimeMillis());
                    PW.println(JSON.toJSONString(new DataPacket("time",time)));
                    PW.flush();
                    break;
                case "Getinformation":
                    String file = req.getParameter("filename");
                    String filepassword = req.getParameter("filePassword");
                    String result = fileServiceFile.UnZipUtils(file,filepassword);
                    TestnewsBen testnewsBen;
                    if(result == UnZipUtils.OperationSuccessful){
                        testnewsBen =fileServiceFile.GetTest(file);
                        req.getSession().setAttribute("test",testnewsBen);
                        PW.println(gson.toJson(new DataPacket("Getinformation",testnewsBen,result)));
                        PW.flush();
                    }else {
                        PW.println(gson.toJson(new DataPacket("Getinformation",result)));
                        PW.flush();
                    }
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
