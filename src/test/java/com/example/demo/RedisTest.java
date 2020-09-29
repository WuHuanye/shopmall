package com.example.demo;

import com.example.demo.utils.RedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RedisTest {
    @Autowired
    private RedisUtils redisUtils;

    @Test
    void redis(){
//        redisUtils.set("accessCount",0);    //访问次数归零
        System.out.println(redisUtils.get("accessCount"));
//        System.out.println(redisUtils.get("hello"));
    }
}
