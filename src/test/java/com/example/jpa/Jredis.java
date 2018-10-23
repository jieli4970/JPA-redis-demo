package com.example.jpa;

import redis.clients.jedis.Jedis;

public class Jredis {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("10.110.200.97");
        System.out.println("success");
        System.out.println("服务正在运行: "+jedis.ping());
        jedis.flushAll();
    }
}
