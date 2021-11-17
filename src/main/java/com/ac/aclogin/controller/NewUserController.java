package com.ac.aclogin.controller;

import com.ac.aclogin.config.MyJedisPoolConfig;
import com.ac.aclogin.service.NewUserService;
import com.ac.aclogin.service.UserService;
import com.ac.aclogin.utils.Result;
import com.ac.aclogin.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: zhanzheng.pang
 * @time: 2021/11/16
 */

@RestController
@RequestMapping("/new")
public class NewUserController {

    @Autowired
    MyJedisPoolConfig myJedisPoolConfig;

    @Autowired
    NewUserService userService;

    @PostMapping("/token")
    public Result token(String userName,String passWord){

//        解决redis乱码问题
//        myJedisPoolConfig.size();

        return userService.setToken(userName,passWord);
    }

    @PostMapping("/getToken")
    public Result getToken(@RequestHeader("Authorization") String token){
        return userService.findUserByToken(token);
    }


}
