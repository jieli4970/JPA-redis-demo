package com.example.jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.scheduling.annotation.EnableScheduling;
import redis.clients.jedis.Jedis;

@SpringBootApplication
@EnableScheduling
@EnableCaching//开启缓存
public class JpaApplication {

    public static void main(String[] args) {

        SpringApplication.run(JpaApplication.class, args);

    }
}
