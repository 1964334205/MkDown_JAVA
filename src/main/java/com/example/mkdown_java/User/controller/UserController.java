package com.example.mkdown_java.User.controller;

import com.example.mkdown_java.User.model.User;
import com.example.mkdown_java.User.service.UserService;
import com.example.mkdown_java.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/User")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result<User> loginController(@RequestBody User user){
        System.out.println(user);
        User Selectuser = userService.selectUser(user);
        if(Selectuser!=null){
            return Result.success(Selectuser,"登录成功！");
        }else{
            return Result.error("123","账号或密码错误！");
        }
    }

    @PostMapping("/register")
    public Result<User> registController(@RequestBody User newUser){
        System.out.println(newUser);
        User user = userService.registService(newUser);
        if(user!=null){
            return Result.success(user,"注册成功！");
        }else{
            return Result.error("456","用户名已存在！");
        }
    }

}
