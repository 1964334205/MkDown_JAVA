package com.example.mkdown_java.User.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mkdown_java.MkDownNote.service.NoteSubmitService;
import com.example.mkdown_java.User.dao.UserDao;
import com.example.mkdown_java.User.model.User;
import com.example.mkdown_java.User.util.UserSession;
import com.example.mkdown_java.common.ResultStatus;
import com.example.mkdown_java.common.exception.ResultException;
import com.example.mkdown_java.config.ResponseResultBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户业务操作
 */
@Service
@ResponseResultBody
public class UserService extends ServiceImpl<UserDao, User> {

    @Autowired
    private NoteSubmitService noteSubmitService;

    boolean flag = false;

    private static final Logger logger
            = LoggerFactory.getLogger(UserService.class);

    /**
     * 登录账户
     * @param user
     * @return
     */
    public String login(User user) throws ResultException {
        //创建查询sql对象
        QueryWrapper<User> QR=new QueryWrapper();
        // 查询用户是否存在
        User selecttuser= getOne(QR.eq("User_Name",user.getUserName()).eq("password",user.getPassword()).last("limit 1"));
        if(selecttuser != null){
            // 设置用户登录Session
            new UserSession().set(selecttuser);
            // 返回user名称
            return selecttuser.getUserName();
        }else {
            // 返回用户ID不存在异常
            throw new ResultException(ResultStatus.USER_ID_NOT_EXIST);
        }
    }

    /**
     * 检测用户是否已经登录
     * @return
     */
    public boolean loggedIn() {
        // 获取用户信息
        User user = new UserSession().get();
        if (user == null){
            return false;
        }else {
            return true;
        }
    }

    /**
     * 退出登录
     * @return
     */
    public void logOut() {
        // 删除用户Session
        new UserSession().invalidate();
    }

    /**
     * 注销账户
     * @return
     */
    public boolean logOff() throws ResultException {
        // 获取用户信息
        User user = new UserSession().get();
        // 删除用户所有笔记
        flag = noteSubmitService.deleteNoteAll(user.getId());
        if(!flag){
            // 返回mysql数据库异常
            throw new ResultException(ResultStatus.INTERNAL_MYSQ_CONNECT_FAIL);
        }
        //退出登录状态
        logOut();
        // 删除当前用户
        flag = this.removeById(user.getId());
        if(!flag){
            // 返回mysql数据库异常
            throw new ResultException(ResultStatus.INTERNAL_MYSQ_CONNECT_FAIL);
        }
        return flag;
//        // 删除删除用户Session
//        new UserSession().invalidate();

    }


    /**
     * 注册账户
     * @param user
     * @return
     */
    public String registService(User user) throws ResultException {
        // sql查询对象
        QueryWrapper<User> QR=new QueryWrapper();
        // 查询用户名是否已存在
        User selectuser= getOne(QR.eq("User_Name",user.getUserName()).last("limit 1"));
        if (selectuser != null){
            throw new ResultException(ResultStatus.USER_NAME_PRESENCE);
        }else {
            // 保存用户信息，创建账户
            boolean file = this.save(user);
            if(!flag){
                // 返回mysql数据库异常
                throw new ResultException(ResultStatus.INTERNAL_MYSQ_CONNECT_FAIL);
            }
            logger.debug("保存用户信息："+user.toString());
            // 登录创建好的对象
            login(user);
            return user.getUserName();
        }
    }
}
