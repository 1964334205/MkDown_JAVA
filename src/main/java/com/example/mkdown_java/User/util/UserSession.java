package com.example.mkdown_java.User.util;

import com.example.mkdown_java.User.model.User;
import com.example.mkdown_java.config.ResponseResultBody;
import jakarta.servlet.http.HttpSession;

/**
 * 用户登录操作类
 */
@ResponseResultBody
public class UserSession {
    // session保存在Redis中的key
    static String USER_SESSION_KEY = "user";

    //
    private HttpSession httpSession;

    /**
     * 获取用户Session
     */
    public UserSession() {
        // 获取当前链接session
        this.httpSession = SessionUtil.getHttpSession();
    }

    /**
     * 设置当前连接的HttpSession
     * @param httpSession
     */
    public UserSession(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    /**
     * 设置用户登录Session
     * session的key为USER_SESSION_KEY
     * @param user
     */
    public void set(User user) {
        httpSession.setAttribute(USER_SESSION_KEY, user);
    }

    /**
     * 获取当前登录用户信息
     * @return
     */
    public User get() {
        //根据当前链接cookie中保存的sessionId和USER_SESSION_KEY获取当前连接的用户信息
        User user = (User) httpSession.getAttribute(USER_SESSION_KEY);
        return user;
    }

    /**
     * 删除Session，退出登录状态
     */
    public void invalidate() {
        httpSession.invalidate();
    }

//    public CommonResult delete(){
//        System.out.println("mockDelete select = ");
//        SessionUtil.deleteHttpSession();
//        return CommonResult.unauthorized("请登录");
//    }

}