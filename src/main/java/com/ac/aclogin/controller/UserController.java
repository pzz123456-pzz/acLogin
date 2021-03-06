package com.ac.aclogin.controller;

import com.ac.aclogin.config.MyJedisPoolConfig;
import com.ac.aclogin.dto.UserDto;
import com.ac.aclogin.pojo.User;
import com.ac.aclogin.service.UserService;
import com.ac.aclogin.utils.MyError;
import com.ac.aclogin.utils.Result;
import com.ac.aclogin.utils.ResultUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * @author: zhanzheng.pang
 * @time: 2021/11/9
 */

@RestController
@RequestMapping("login")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    MyJedisPoolConfig myJedisPoolConfig;


    /**
     * 登录
        * @Param: userName  , passWord
        * @return: java.lang.String
        * @Author: zhanzheng.pang
        * @Date: 2021/11/10 10:49
    */
    @GetMapping("/login1")
    public Result selectOne(String userName,String passWord){
        return userService.selectOne(userName,passWord);

    }

    /**
     * 使用spring-boot-starter-data-redis依赖 的连接池
     * 需要配置yml文件
        * @Param: userName  , passWord
        * @return: java.lang.String
        * @Author: zhanzheng.pang
        * @Date: 2021/11/11 14:54
    */
    @GetMapping("/login2")
    public Result selectOne1(String userName,String passWord){

        /**
         * 解决redisTemplate乱码问题
         */
        myJedisPoolConfig.size();

        return userService.selectOne1(userName,passWord);
    }



    /**验证注册  dto 返回自定义的 Result
        * @Param: userDto  , errors
        * @return: com.ac.aclogin.utils.Result
        * @Author: zhanzheng.pang
        * @Date: 2021/11/15 15:39
    */
    @PostMapping("/register1")
    public Result registerDto(@Validated @RequestBody UserDto userDto, Errors errors){
        if (errors.hasErrors()){
            /**
             *  获取dto中的默认的msg信息  errors.getFieldError().getDefaultMessage()
             * */
            return ResultUtil.fail(errors.getFieldError().getDefaultMessage());
        }

        return userService.selectOne(userDto);

    }


    /**
     * 验证注册  dto
        * @Param: userDto  , errors
        * @return: java.lang.String
        * @Author: zhanzheng.pang
        * @Date: 2021/11/15 15:38
    */
    @PostMapping("/register2")
    public Result registerDto1(@Validated @RequestBody UserDto userDto, Errors errors) {
        if (errors.hasErrors()) {
            return ResultUtil.fail(MyError.My_ERROR_8.getMsg());
        }

        return userService.selectOne(userDto);
    }
}
