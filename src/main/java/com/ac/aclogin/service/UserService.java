package com.ac.aclogin.service;

import com.ac.aclogin.pojo.User;
import com.ac.aclogin.utils.Result;

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

    Result selectOne(String userName,String passWord);

    Result selectOne1(String userName,String passWord);



    /**
     * 注册插入
     * @param userName
     * @param passWord
     * @return int
     */
    int insert(String userName,String passWord);
}
