package top.openfbi.mdnote.user.util;

import jakarta.servlet.http.HttpSession;
import top.openfbi.mdnote.config.ResponseResultBody;
import top.openfbi.mdnote.user.model.User;

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
        httpSession = SessionUtil.getHttpSession();
    }

    /**
     * 设置当前连接的HttpSession
     */
    public UserSession(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    /**
     * 设置用户登录Session
     * session的key为USER_SESSION_KEY
     * @param user
     */
    public static void set(User user) {
        new UserSession().httpSession.setAttribute(USER_SESSION_KEY, user);
    }

    /**
     * 获取当前登录用户信息
     * @return
     */
    public static User get() {
        //根据当前链接cookie中保存的sessionId和USER_SESSION_KEY获取当前连接的用户信息
        return (User) new UserSession().httpSession.getAttribute(USER_SESSION_KEY);
    }

    /**
     * 删除Session，退出登录状态
     */
    public void invalidate() {
        httpSession.invalidate();
    }
}