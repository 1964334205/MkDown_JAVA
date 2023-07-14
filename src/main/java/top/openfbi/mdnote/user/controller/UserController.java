package top.openfbi.mdnote.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.openfbi.mdnote.common.Result;
import top.openfbi.mdnote.common.exception.ResultException;
import top.openfbi.mdnote.config.ResponseResultBody;
import top.openfbi.mdnote.user.service.UserService;
import top.openfbi.mdnote.user.util.UserSession;

/**
 * 用户操作接口
 */
@RequestMapping("/user")
@ResponseResultBody
@RestController()
public class UserController {

    @Autowired
    private UserService userService;

    private static final Logger logger
            = LoggerFactory.getLogger(UserController.class);




    /**
     * 注销账户
     */
    @GetMapping("/logOff")
    public Result logOff() throws ResultException {
        logger.info("删除 User");
        userService.logOff(UserSession.get().getId());
        return Result.success();
    }

}
