package com.example.mkdown_java.User.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mkdown_java.User.dao.UserDao;
import com.example.mkdown_java.User.model.User;
import com.example.mkdown_java.common.UUIDUtil;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ServiceImpl<UserDao, User> {

    public User selectUser(User user) {
        QueryWrapper<User> QR=new QueryWrapper();
        User selecttuser= getOne(QR.eq("User_Name",user.getUserName()).eq("User_Password",user.getUserPassword()).last("limit 1"));
        return selecttuser;
    }

    public User registService(User user) {
        QueryWrapper<User> QR=new QueryWrapper();
        User selectuser= getOne(QR.eq("User_Name",user.getUserName()).last("limit 1"));
        if (selectuser != null){
            return null;
        }else {
            //返回创建好的用户对象(带uid)
            user.setUserId(UUIDUtil.getUUID());
            boolean file = this.save(user);
            if(file){
                user.setUserPassword("");
                user.setUserPasswordVerify("");
            }
            return user;
        }
    }
}
