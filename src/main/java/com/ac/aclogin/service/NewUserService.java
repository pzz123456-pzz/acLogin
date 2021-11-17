package com.ac.aclogin.service;

import com.ac.aclogin.utils.Result;

/**
 * @author: zhanzheng.pang
 * @time: 2021/11/17
 */
public interface NewUserService {

    /**设置 token  */
    Result setToken(String userName, String passWord);

    /** 根据touken查找用户 */
    Result findUserByToken(String token);
}
