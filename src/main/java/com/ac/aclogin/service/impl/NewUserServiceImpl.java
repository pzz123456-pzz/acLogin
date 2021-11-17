package com.ac.aclogin.service.impl;

import com.ac.aclogin.mapper.UserMapper;
import com.ac.aclogin.pojo.User;
import com.ac.aclogin.service.NewUserService;
import com.ac.aclogin.utils.JwtUtil;
import com.ac.aclogin.utils.Result;
import com.ac.aclogin.utils.ResultUtil;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author: zhanzheng.pang
 * @time: 2021/11/17
 */
@Service
public class NewUserServiceImpl implements NewUserService {

    @Autowired
    RedisTemplate redisTemplate;

    @Resource
    UserMapper userMapper;

    @Override
    public Result setToken(String userName, String passWord) {
        User user = userMapper.selectOne(userName);
        System.out.println(user.getUserId());
        if (user == null){
            return ResultUtil.fail("没有这个人");
        }
        if (user.getUserName().equals(userName) && user.getPassWord().equals(passWord)){
//            生成token     body里是user对象
            String token = JwtUtil.createToken(user);
//            放入redis 有效期为一天
            redisTemplate.opsForValue().set("TOKEN_" + token, user,1, TimeUnit.DAYS);

            return ResultUtil.success(token);
        }
        return ResultUtil.fail("用户名或密码错误!");
    }

    @Override
    public Result findUserByToken(String token) {
//        token是空的时候
        if (StringUtils.isBlank(token)){
            return ResultUtil.fail("token不存在");
        }
//        解析出来B部分没有数据
        Map<String, Object> map = JwtUtil.cheakToken(token);
        if (map == null){
            return ResultUtil.fail("没有数据");
        }
//        到redis中查询token是否过期
        Object o = redisTemplate.opsForValue().get("TOKEN_" + token);
//        redis中过期了
        if (o == null){
            return ResultUtil.fail("redis没有，已经过期");
        }
//        redis 中没有过期并返回value
        return ResultUtil.success(o);
    }

}
