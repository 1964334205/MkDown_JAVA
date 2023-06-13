package com.example.mkdown_java.User.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mkdown_java.MkDownNote.service.NoteSubmitService;
import com.example.mkdown_java.User.dao.UserDao;
import com.example.mkdown_java.User.model.User;
import com.example.mkdown_java.User.util.UserSession;
import com.example.mkdown_java.common.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ServiceImpl<UserDao, User> {

    @Autowired
    private NoteSubmitService noteSubmitService;

    private static final Logger logger
            = LoggerFactory.getLogger(UserService.class);

    /**
     * 登录账户
     * @param user
     * @return
     */
    public CommonResult login(User user) {
        //创建查询sql对象
        QueryWrapper<User> QR=new QueryWrapper();
        // 查询用户是否存在
        User selecttuser= getOne(QR.eq("User_Name",user.getUserName()).eq("password",user.getPassword()).last("limit 1"));
        if(selecttuser != null){
            // 设置用户登录Session
            new UserSession().set(selecttuser);
            // 返回user名称
            return CommonResult.success(selecttuser.getUserName());
        }else {
            return CommonResult.failed("登录失败");
        }
    }

    /**
     * 退出登录
     * @return
     */
    public CommonResult logOut() {
        // 删除用户Session
        new UserSession().invalidate();
        return CommonResult.success("退出成功");
    }

    /**
     * 注销账户
     * @return
     */
    public CommonResult logOff() {
        // 获取用户信息
        User user = new UserSession().get();
        // 删除用户所有笔记
        noteSubmitService.deleteNoteAll(user.getId());
        //退出登录状态
        logOut();
        // 删除当前用户
        this.removeById(user.getId());
//        // 删除删除用户Session
//        new UserSession().invalidate();
        return CommonResult.success("注销成功");
    }


    /**
     * 注册账户
     * @param user
     * @return
     */
    public CommonResult registService(User user) {
        // sql查询对象
        QueryWrapper<User> QR=new QueryWrapper();
        // 查询用户名是否已存在
        User selectuser= getOne(QR.eq("User_Name",user.getUserName()).last("limit 1"));
        if (selectuser != null){
            return CommonResult.failed("注册失败，请更换账户名");
        }else {
            //返回创建好的用户对象(带uid)
//            user.setId(UUIDUtil.getUUID());
            // 保存对象
            boolean file = this.save(user);
            logger.debug("保存用户信息："+user.toString());
            // 登录创建好的对象
            login(user);
            return CommonResult.success(user.getUserName());
        }
    }
}
