package com.ac.aclogin.controller;

import com.ac.aclogin.dto.UserDto;
import com.ac.aclogin.pojo.User;
import com.ac.aclogin.service.UserService;
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
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

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


    /**
     * 登录
        * @Param: userName  , passWord
        * @return: java.lang.String
        * @Author: zhanzheng.pang
        * @Date: 2021/11/10 10:49
    */
    @GetMapping("/login1")
    public String selectOne(String userName,String passWord){

        /**
         * 设置jedis的配置信息
         * 使用的是jedis依赖
         */
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//       设置最大连接数
        jedisPoolConfig.setMaxTotal(10);
//        设置最多空闲连接数
        jedisPoolConfig.setMaxIdle(10);
//        设置最少空闲连接数
        jedisPoolConfig.setMinIdle(0);
        JedisPool jedisPool = new JedisPool(jedisPoolConfig,"127.0.0.1",6379,1000,
                "123456",3);

        Jedis jedis = jedisPool.getResource();

        if (jedis.get(userName) != null){
            if (jedis.get(userName).equals(passWord)){
                jedis.close();
                return  "这是redis 的数据 ：" + userName + "登录成功,密码：" + passWord;
            }else {
                jedis.close();
                return " 这是redis 的数据  ： 密码错误，登陆失败";
            }
        }else {
            User user = userService.selectOne(userName);
            if (user != null){
                if (user.getUserName().equals(userName) && user.getPassWord().equals(passWord)) {
                    jedis.set(user.getUserName(),user.getPassWord());
                    jedis.close();
                    return "登录成功";
                }
            }
            return "登录失败,请先注册！";
        }
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
    public String selectOne1(String userName,String passWord){

        /**
         * 解决redisTemplate乱码问题
         */
        Jackson2JsonRedisSerializer jackson2JsonRedisSerialize = new Jackson2JsonRedisSerializer(Object.class);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(jackson2JsonRedisSerialize);

        if (redisTemplate.opsForValue().get(userName) != null){
            if (redisTemplate.opsForValue().get(userName).equals(passWord)){
                return  "这是redis 的数据 ：" + userName + "登录成功,密码：" + passWord;
            }else {
                return " 这是redis 的数据  ： 密码错误，登陆失败";
            }
        }else {
            User user = userService.selectOne(userName);
            if (user != null){
                if (user.getUserName().equals(userName) && user.getPassWord().equals(passWord)) {
                    redisTemplate.opsForValue().set(user.getUserName(),user.getPassWord());
                    return "登录成功";
                }
            }
            return "登录失败,请先注册！";
        }
    }

    /**注册
        * @Param: userName  , passWord
        * @return: java.lang.String
        * @Author: zhanzheng.pang
        * @Date: 2021/11/10 10:49
    */
    @PostMapping("/register")
    public String register(String userName,String passWord){
        User user = userService.selectOne(userName);
        if (userName == null || userName == ""){
            return "用户名不能为空";
        }else if (passWord == null || passWord == ""){
            return "密码不能为空";
        }else if (user == null){
            int i = userService.insert(userName,passWord);
            if (i == 1){
                return "注册成功";
            }
        }else {
            return "用户名已存在";
        }
        return "注册失败";
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
            return ResultUtil.fail("参数有错误");
        }
        User u = new User();
//        根据名字查找是否存在这个用户名
        User user = userService.selectOne(userDto.getUserName());
//        将userDto的内容给u
        BeanUtils.copyProperties(userDto,u);
        if (user == null){
            int i = userService.insert(u.getUserName(),u.getPassWord());
            if (i == 1){
                return ResultUtil.success("成功");
            }
        }else {
            return ResultUtil.fail("用户名已存在");
        }
        return ResultUtil.fail("注册失败");
    }


    /**
     * 验证注册  dto
        * @Param: userDto  , errors
        * @return: java.lang.String
        * @Author: zhanzheng.pang
        * @Date: 2021/11/15 15:38
    */
    @PostMapping("/register2")
    public String registerDto1(@Validated @RequestBody UserDto userDto, Errors errors){
        if (errors.hasErrors()){
            return "参数有错误";
        }
        User u = new User();
//        根据名字查找是否存在这个用户名
        User user = userService.selectOne(userDto.getUserName());
//        将userDto的内容给u
        BeanUtils.copyProperties(userDto,u);
        if (user == null){
            int i = userService.insert(u.getUserName(),u.getPassWord());
            if (i == 1){
                return "成功";
            }
        }else {
            return "用户名已存在";
        }
        return "注册失败";
    }
}
