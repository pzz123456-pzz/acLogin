package com.ac.aclogin.service.impl;

import com.ac.aclogin.config.MyJedisPoolConfig;
import com.ac.aclogin.dto.UserDto;
import com.ac.aclogin.mapper.UserMapper;
import com.ac.aclogin.pojo.User;
import com.ac.aclogin.service.UserService;
import com.ac.aclogin.utils.JwtUtil;
import com.ac.aclogin.utils.MyError;
import com.ac.aclogin.utils.Result;
import com.ac.aclogin.utils.ResultUtil;
import com.alibaba.fastjson.JSON;
import io.netty.util.internal.StringUtil;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author: zhanzheng.pang
 * @time: 2021/11/9
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    RedisTemplate redisTemplate;

    @Resource
    UserMapper userMapper;

    @Resource
    MyJedisPoolConfig myJedisPoolConfig;

    @Override
    public Result selectOne(UserDto userDto) {
        User u = new User();
        //        将userDto的内容给u
        BeanUtils.copyProperties(userDto,u);
        User user = userMapper.selectOne(u.getUserName());
        if (user == null){
            int i = userMapper.insert(u.getUserName(),u.getPassWord());
            if (i == 1){
                return ResultUtil.success(u.toString());
            }
        }else {
            return ResultUtil.fail(MyError.My_ERROR_6.getMsg());
        }
        return ResultUtil.fail(MyError.My_ERROR_7.getMsg());
    }

    @Override
    public Result selectOne(String userName,String passWord) {

        if (redisTemplate.opsForValue().get(userName) != null){
            if (redisTemplate.opsForValue().get(userName).equals(passWord)){
                return  ResultUtil.success( "这是redis 的数据: 成功");
            }else {
                return  ResultUtil.fail(" 这是redis 的数据  ： " + MyError.MY_ERROR_2.getMsg());
            }
        }else {
            User user = userMapper.selectOne(userName);
            if (user != null){
                if (user.getUserName().equals(userName) && user.getPassWord().equals(passWord)) {
                    redisTemplate.opsForValue().set(user.getUserName(),user.getPassWord());
                    return ResultUtil.success("成功");
                }
            }
            return ResultUtil.fail(MyError.MY_ERROR_1.getMsg());
        }
    }

    @Override
    public Result selectOne1(String userName, String passWord) {
        Jedis jedis = myJedisPoolConfig.setJedisPoolConfig();

            if (!StringUtils.isBlank(jedis.get(userName))){
                if (jedis.get(userName).equals(passWord)){
                    jedis.close();
                    return  ResultUtil.success( "这是redis 的数据: 成功");
                }else {
                    jedis.close();
                    return  ResultUtil.fail(" 这是redis 的数据  ："+ MyError.MY_ERROR_2.getMsg());
                }
            }else {
                User user = userMapper.selectOne(userName);
                if (user != null){
                    if (user.getUserName().equals(userName) && user.getPassWord().equals(passWord)) {
                        jedis.set(user.getUserName(),user.getPassWord());
                        jedis.close();
                        return ResultUtil.success("登录成功");
                    }
                }
                return ResultUtil.fail(MyError.MY_ERROR_1.getMsg());
            }
        }


    @Override
    public int insert(String userName, String passWord) {
        return userMapper.insert(userName,passWord);
    }
}
