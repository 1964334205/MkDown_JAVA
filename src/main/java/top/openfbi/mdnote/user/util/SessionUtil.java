package top.openfbi.mdnote.user.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 获取当前连接的Session
 */
@Service
public class SessionUtil {

    /**
     * 获取当前链接HttpServletRequest
     * @return
     */
    public static  HttpServletRequest getRequest(){
        // 获取当前线程绑定的RequestAttributes
        ServletRequestAttributes servletRequestAttributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
        assert servletRequestAttributes != null;
        return servletRequestAttributes.getRequest();
    }

    /**
     * 获取当前链接HttpSession
     * @return
     */
    public static  HttpSession getHttpSession(){
        // 获取当前链接httpServletRequest
        HttpServletRequest httpServletRequest  = getRequest();
        // 返回当前链接Session
        return httpServletRequest.getSession();
    }

    /**
     * 删除Session，退出登录状态
     */
    public static void deleteHttpSession(){
        // invalidate删除esssion
        getRequest().getSession().invalidate();
    }




}
