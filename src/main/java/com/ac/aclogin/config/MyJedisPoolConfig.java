package com.ac.aclogin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.Resource;

/**
 * @author: zhanzheng.pang
 * @time: 2021/11/16
 */
@Configuration
public class MyJedisPoolConfig {

    @Autowired
    RedisTemplate redisTemplate;
    
    /**
     * 设置jedis的配置信息
     * 使用的是jedis依赖
     */
    public Jedis setJedisPoolConfig(){
        JedisPoolConfig jedisPoolConfig =  new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(10);
        jedisPoolConfig.setMaxTotal(10);
        jedisPoolConfig.setMinIdle(0);

        JedisPool jedisPool = new JedisPool(jedisPoolConfig,"127.0.0.1",6379,1000,
                "123456",3);

        return jedisPool.getResource();
    }

    public void size(){
        /**
         * 解决redisTemplate乱码问题
         */
//        Jackson2JsonRedisSerializer jackson2JsonRedisSerialize = new Jackson2JsonRedisSerializer(Object.class);
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(jackson2JsonRedisSerialize);
    }

}
