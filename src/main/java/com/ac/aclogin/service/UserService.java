package com.ac.aclogin.service;

import com.ac.aclogin.pojo.User;

import java.util.List;

/**
 * @author: zhanzheng.pang
 * @time: 2021/11/9
 */
public  interface UserService {

    /**
     * 通过用户名查找一个用户对象
     * @param userName
     * @return User
     */
    User selectOne(String userName);

    /**
     * 注册插入
     * @param userName
     * @param passWord
     * @return int
     */
    int insert(String userName,String passWord);
}
