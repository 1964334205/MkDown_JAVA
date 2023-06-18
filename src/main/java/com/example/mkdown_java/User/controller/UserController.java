package com.example.mkdown_java.User.controller;

import com.example.mkdown_java.User.model.User;
import com.example.mkdown_java.User.service.UserService;
import com.example.mkdown_java.common.Result;
import com.example.mkdown_java.common.exception.ResultException;
import com.example.mkdown_java.config.ResponseResultBody;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户操作接口
 */
@RequestMapping("/User")
@ResponseResultBody
@RestController()
public class UserController {

    @Autowired
    private UserService userService;

    private static final Logger logger
            = LoggerFactory.getLogger(UserController.class);

    @Data
    @AllArgsConstructor
    class LoginResponse {
            private String userName;

    }

    /**
     * 登录账户
     *
     * @param user
     * @return
     */
    @PostMapping(value = "/login")
    public LoginResponse loginController(@RequestBody User user) throws ResultException {
        logger.debug("登录 账户" + user);
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
     * loggedIn方法专门的返回值
     * 原因：springboot会把string返回值给识别成html结构，导致无法序列化为Result类型。抛出类型转换异常。
     * 所以使用类的方式返回数据
     * 数据最好使用对象形式返回
     */
    @Data
    @AllArgsConstructor
    class LoggedInResponse {
        private boolean loggedIn;

    }

    /**
     * 检测用户是否已经登录
     * @return
     */
    @GetMapping("/loggedIn")
    public LoggedInResponse loggedInController() {
        logger.debug("检测用户是否已登录账户");
        return new LoggedInResponse(userService.loggedIn());
    }


    /**
     * 测试
     *
     * @param
     * @return
     */
    @GetMapping("/cs")
    public Result cs() throws Exception {
        throw new Exception("helloError");
    }

    /**
     * 退出登录
     *
     * @return
     */
    @GetMapping("/logOut")
    public void logOutController() {
        logger.debug("删除 success");
        userService.logOut();
    }

    /**
     * 注销账户
     *
     * @return
     */
    @GetMapping("/logOff")
    public void logOffController() throws ResultException {
        logger.debug("删除 User");
        userService.logOff();
    }

    /**
     * 注册账户
     *
     * @param newUser
     * @return
     */
    @PostMapping("/register")
    public LoginResponse registController(@RequestBody User newUser) throws ResultException {
        return new LoginResponse(userService.registService(newUser));
    }
}
