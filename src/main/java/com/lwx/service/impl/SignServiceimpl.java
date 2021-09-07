package com.lwx.service.impl;

import com.lwx.object.Admin;
import com.lwx.object.AdminList;
import com.lwx.service.SignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @version 1.0
 * @author: xingyan
 * @date: 2021/6/7 13:33
 * @desc: 管理员账号登录检测业务层
 */
@Service
public class SignServiceimpl implements SignService {
    private final String SuccessfulLogin = "SuccessfulLogin";       //登录成功
    private final String AccountDoesNotExist = "AccountDoesNotExist";   //账号不存在
    private final String PasswordError = "PasswordError";           //密码错误
    private final String AccountPasswordIsEmpty = "AccountPasswordIsEmpty"; //账号密码为空
    private final String UnknownError = "UnknownError";     //未知错误检测失败
    @Autowired
    private AdminList adminList;
    @Override
    public String Sign(String acc, String pwd, HttpServletRequest req) {
        {
            if(acc.length()>=6&&pwd.length()>=6){
                for(Admin admin:adminList){
                    if(admin.getAccount().equals(acc)&&admin.getPassword().equals(pwd)){
                        req.getSession().setAttribute("Admin",admin);
                        return SuccessfulLogin;
                    }else if(admin.getAccount().equals(acc)&&!admin.getPassword().equals(pwd)){
                        return PasswordError;
                    }
                    if (adminList.get(adminList.size()-1)==admin){
                        return AccountDoesNotExist;
                    }
                }
            }else{
                return AccountPasswordIsEmpty;
            }
            return UnknownError;
        }
    }
}
