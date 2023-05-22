package com.example.mkdown_java.User.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mkdown_java.User.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends BaseMapper<User> {

}