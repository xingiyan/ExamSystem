package com.lwx.service;



import com.lwx.bean.TestnewsBen;
import com.lwx.servlet.Socket.TestSocket;

import javax.websocket.Session;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @version 1.0
 * @author: xingyan
 * @date: 2021/6/19 21:32
 * @desc:
 */
public interface Testoperation {
    public TestnewsBen Room(String TestName,Session session);
}
