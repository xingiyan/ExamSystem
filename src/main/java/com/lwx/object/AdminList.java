package com.lwx.object;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @version 1.0
 * @author: xingyan
 * @date: 2021/6/4 15:03
 * @desc:  构建集合管理员集合
 */
@Component
public class AdminList extends ArrayList<Admin> {
    public AdminList(){
        super();
        String AdminListString  = null;
        Properties properties = new Properties();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream
                    (AdminList.class.getResource("/configure/Admin.properties").getFile()), "UTF-8"));
            properties.load(bufferedReader);
            AdminListString = properties.getProperty("AdminList");
            List adminList = JSON.parseArray(AdminListString,Admin.class);
            this.addAll(adminList);
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
    }
}
