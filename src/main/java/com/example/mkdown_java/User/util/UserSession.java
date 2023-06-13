package com.example.mkdown_java.User.util;

import com.example.mkdown_java.User.model.User;
import jakarta.servlet.http.HttpSession;


public class UserSession {
    static String USER_SESSION_KEY = "user";

    private HttpSession httpSession;

    /**
     * 获取用户Session
     */
    public UserSession() {
        // 获取当前链接session
        this.httpSession = SessionUtil.getHttpSession();
    }

    /**
     * 根据HttpSession 获取session
     * @param httpSession
     */
    public UserSession(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    /**
     * 设置用户登录Session
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
        //根据当前链接cookie名称获取session的id获取，得到session信息
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