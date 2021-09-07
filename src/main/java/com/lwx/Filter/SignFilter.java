package com.lwx.Filter;

import com.lwx.bean.TestnewsBen;
import com.lwx.object.Admin;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @version 1.0
 * @author: xingyan
 * @date: 2021/6/10 15:09
 * @desc:
 */
@WebFilter(urlPatterns = "/*")
public class SignFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;

        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String path =  req.getRequestURI();

        Admin admin = (Admin)req.getSession().getAttribute("Admin");
        if(path.indexOf("jsp/menu.jsp") != -1||path.indexOf("NewTest.jsp") != -1||path.indexOf("StudentList.jsp") != -1||path.indexOf("AdminTest.jsp") != -1){
            if(admin!=null){
                filterChain.doFilter(servletRequest,servletResponse);//放行
            }else{
//                servletRequest.setCharacterEncoding("utf-8");
                servletResponse.setContentType("text/html;charset=UTF-8");
                PrintWriter pw = resp.getWriter();
                pw.println("<script type='text/javascript'> alert('请先登录后操作');window.location.href='"+"/"+path.split("/")[1]+"/jsp/Sign.jsp';</script>");
                pw.flush();
            }
        }else if (path.indexOf("jsp/examination.jsp") != -1){
            TestnewsBen testnewsBen = (TestnewsBen)req.getSession().getAttribute("test");
            if(testnewsBen==null){
                servletResponse.setContentType("text/html;charset=UTF-8");
                PrintWriter pw = resp.getWriter();
                pw.println("<script type='text/javascript'> alert('未能获取到试卷信息，请重试');window.location.href='"+"/"+path.split("/")[1]+"/jsp/welcome.jsp';</script>");
                pw.flush();
            }else{
                filterChain.doFilter(servletRequest,servletResponse);//放行
            }
        }else{
            filterChain.doFilter(servletRequest,servletResponse);//放行
        }

    }

    @Override
    public void destroy() {

    }
}
