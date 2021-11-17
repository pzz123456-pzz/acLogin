package com.ac.aclogin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class AcloginApplicationTests {
    @Autowired
    RedisTemplate redisTemplate;

    @Test
    void contextLoads() {
        Object o = redisTemplate.opsForValue().get("s");
        System.out.println(o);
    }

}
