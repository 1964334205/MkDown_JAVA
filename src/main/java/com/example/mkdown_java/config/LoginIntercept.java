package com.example.mkdown_java.config;

import com.example.mkdown_java.User.util.UserSession;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.io.PrintWriter;

@Component
public class LoginIntercept implements HandlerInterceptor {

    private static final Logger logger
            = LoggerFactory.getLogger(LoginIntercept.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        // 1.得到 HttpSession 对象
        HttpSession httpSession = request.getSession(false);


        if (httpSession != null && new UserSession(httpSession).get() != null) {
            // 表示已经登录
            logger.debug("已登录");
            return true;
        }
        // 执行到此代码表示未登录，未登录就跳转到登录页面
//        response.sendRedirect("/login.html");
        logger.debug("被拦截");
//        response.sendRedirect("/Login");
        returnJson(response,"{\"code\":-1,\"msg\":\"name is error!\"}");
        return false;
    }

    /*返回客户端数据*/
    private void returnJson(HttpServletResponse response, String json) throws Exception {
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(json);

        } catch (IOException e) {
        } finally {
            if (writer != null)
                writer.close();
        }
    }
}
