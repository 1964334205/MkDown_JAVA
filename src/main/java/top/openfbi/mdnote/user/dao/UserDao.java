package top.openfbi.mdnote.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.openfbi.mdnote.user.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends BaseMapper<User> {

}