package com.lwx.service;

import com.lwx.object.Admin;

import javax.servlet.http.HttpServletRequest;

/**
 * @version 1.0
 * @author: xingyan
 * @date: 2021/6/4 18:57
 * @desc:   登录业务
 */
public interface SignService {
    public String Sign(String acc, String pwd, HttpServletRequest req);
}
