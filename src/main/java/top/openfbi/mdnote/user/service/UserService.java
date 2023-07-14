package top.openfbi.mdnote.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.openfbi.mdnote.common.ResultStatus;
import top.openfbi.mdnote.common.exception.ResultException;
import top.openfbi.mdnote.config.ResponseResultBody;
import top.openfbi.mdnote.note.service.NoteService;
import top.openfbi.mdnote.user.dao.UserDao;
import top.openfbi.mdnote.user.model.User;
import top.openfbi.mdnote.user.util.UserSession;

/**
 * 用户业务操作
 */
@Service
@ResponseResultBody
public class UserService {

    @Autowired
    private NoteService noteService;

    @Autowired
    UserDao userDao;

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
        User selecttuser= userDao.selectOne(QR.eq("user_name",user.getUserName()));
        if(selecttuser == null){
            // 返回用户ID不存在异常
            throw new ResultException(ResultStatus.USER_ID_NOT_EXIST);
        }
        if (!selecttuser.getPassword().equals(user.getPassword())){
            logger.info("登录账户,密码错误: {}",user);
            // 返回用户密码错误
            throw new ResultException(ResultStatus.USER_PASSWORD_FAIL);
        }
        // 设置用户登录Session
        UserSession.set(selecttuser);
        // 返回user名称
        return selecttuser.getUserName();


    }


    /**
     * 退出登录
     * @return
     */
    public void logOut(Long userId) {
        // 删除用户Session
        new UserSession().invalidate();
        logger.info("用户退出成功，用户ID: {}",userId);
    }

    /**
     * 注销账户
     * @return
     */
    public void logOff(Long userId) throws ResultException {
        logger.info("删除账户，账户ID: {}",userId);
        // 删除用户所有笔记
        noteService.deleteAll(userId);
        //退出登录状态
        logOut(userId);
        // 删除当前用户
        if(userDao.deleteById(userId) != 1){
            logger.error("删除用户失败，用户ID: {}",userId);
            // 返回mysql数据库异常
            throw new ResultException(ResultStatus.INTERNAL_MYSQL_SQL_EXEC_FAIL);
        }
    }


    /**
     * 注册账户
     * @param user
     * @return
     */
    public String registService(User user) throws ResultException {

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.in("user_name",user.getUserName());
        if(userDao.selectOne(wrapper) != null){
            logger.info("用户名已存在: {}",user.getUserName());
            // 返回mysql数据库异常
            throw new ResultException(ResultStatus.USER_NAME_PRESENCE);
        }
        // 保存用户信息，创建账户
        userDao.insert(user);
        // 登录创建好的对象
        // 设置用户登录Session
        UserSession.set(user);
        return user.getUserName();

    }
}
