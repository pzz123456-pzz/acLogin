package com.ac.aclogin.service.impl;

import com.ac.aclogin.mapper.UserMapper;
import com.ac.aclogin.pojo.User;
import com.ac.aclogin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: zhanzheng.pang
 * @time: 2021/11/9
 */
@Service
public class UserServiceImpl implements UserService {


    @Autowired
    UserMapper userMapper;

    @Override
    public User selectOne(String userName) {
        return userMapper.selectOne(userName);
    }

    @Override
    public int insert(String userName, String passWord) {
        return userMapper.insert(userName,passWord);
    }
}
