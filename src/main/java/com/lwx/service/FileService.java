package com.lwx.service;

import com.lwx.bean.TestnewsBen;

import java.io.File;

/**
 * @version 1.0
 * @author: xingyan
 * @date: 2021/6/7 14:05
 * @desc: 文件业务上传 解析
 */
public interface FileService {
//    文件上传
    public boolean File(String Name,String File,String FileClass,boolean fileonly);
//    文件 解压
    public String UnZipUtils(String Name,String password);
    //文件解析
    public TestnewsBen GetTest(String Name);
}
