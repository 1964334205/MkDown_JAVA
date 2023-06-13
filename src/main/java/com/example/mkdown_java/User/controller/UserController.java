package com.example.mkdown_java.User.controller;

import com.example.mkdown_java.User.model.User;
import com.example.mkdown_java.User.service.UserService;
import com.example.mkdown_java.common.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/User")
public class UserController {

    @Autowired
    private UserService userService;

    private static final Logger logger
            = LoggerFactory.getLogger(UserController.class);

    /**
     * 登录账户
     * @param user
     * @return
     */
    @PostMapping("/login")
    public CommonResult loginController(@RequestBody User user){
        logger.debug("登录 账户" + user);
        return userService.login(user);
    }

    /**
     * 退出登录
     * @return
     */
    @GetMapping("/logOut")
    public CommonResult logOutController(){
        logger.debug("删除 success");
        return userService.logOut();
    }

    /**
     * 注销账户
     * @return
     */
    @GetMapping("/logOff")
    public CommonResult logOffController(){
        logger.debug("删除 User");
        return userService.logOff();
    }

    /**
     * 注册账户
     * @param newUser
     * @return
     */
    @PostMapping("/register")
    public CommonResult registController(@RequestBody User newUser){
        return userService.registService(newUser);
    }
}
