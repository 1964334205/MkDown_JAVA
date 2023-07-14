package top.openfbi.mdnote.user.controller;


import top.openfbi.mdnote.common.exception.ResultException;
import top.openfbi.mdnote.config.ResponseResultBody;
import top.openfbi.mdnote.user.model.User;
import top.openfbi.mdnote.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.openfbi.mdnote.user.util.UserSession;

/**
 * 登录操作接口
 */
@RequestMapping("/passport")
@ResponseResultBody
@RestController()
public class PassportController {

    @Autowired
    private UserService userService;

    private static final Logger logger
            = LoggerFactory.getLogger(UserController.class);

    @Data
    @AllArgsConstructor
    static class RegistResponse {
        private String userName;
    }
    // 注册账户
    @PostMapping("/register")
    public RegistResponse regist(@RequestBody User newUser) throws ResultException {
        return new RegistResponse(userService.registService(newUser));
    }

    @Data
    @AllArgsConstructor
    static class LoginResponse {
        private String userName;
    }
    /**
     * 登录账户
     */
    @PostMapping(value = "/login")
    public LoginResponse login(@RequestBody User user) throws ResultException {
//        return userService.login(user);
        //下面3种方案都可以，直接使用result不推荐，不具有扩展性，升级接口时，前端调用方会挂掉
        //这种方式最好
        return new LoginResponse(userService.login(user));
        //其次是返回一个map
//        return new HashMap<String, Object>() {{
//            put("userName", userService.login(user));
//        }};
        //这种方式最差
//        return Result.success(userService.login(user));
    }



    /**
     * IsInfoResponse方法专门的返回值
     */
    @Data
    @AllArgsConstructor
    static class IsInfoResponse {
        private boolean loggedIn;
        private String userName;
    }

    /**
     * 检测用户是否已经登录
     * @return
     */
    @GetMapping("/isLogin")
    public IsInfoResponse isLogin() {
        return new IsInfoResponse(true,"");
    }

    /**
     * 退出登录
     *
     * @return
     */
    @GetMapping("/logOut")
    public void logOut() {
        userService.logOut(UserSession.get().getId());
    }
}
