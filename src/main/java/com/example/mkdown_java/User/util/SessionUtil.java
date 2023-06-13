package com.example.mkdown_java.User.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class SessionUtil {

    /**
     * 获取当前链接HttpServletRequest
     * @return
     */
    public static  HttpServletRequest getRequest(){
        ServletRequestAttributes servletRequestAttributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
        return servletRequestAttributes.getRequest();
    }

    /**
     * 获取当前链接HttpSession
     * @return
     */
    public static  HttpSession getHttpSession(){
        // 获取当前链接httpServletRequest
        HttpServletRequest httpServletRequest  = getRequest();
        return httpServletRequest.getSession();
    }

    /**
     * 删除Session，退出登录状态
     */
    public static void deleteHttpSession(){
        getRequest().getSession().invalidate();
    }




}
