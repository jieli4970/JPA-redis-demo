package com.example.jpa.service.redis.impl;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;

import com.example.jpa.service.redis.RedisService;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;


@Service
public class RedisServiceImpl implements RedisService {



    @Resource
    private RedisTemplate<String, Object> redisTemplate;

//  todo 2018-10-22 16:12:56 刷新数据库缓存，清空已有数据
    public void flushdb() {
        redisTemplate.execute(new RedisCallback<Object>() {
            public String doInRedis(RedisConnection connection)  {
                connection.flushDb();
                return "ok";
            }
        });
    }



    /**
     * 获取操作字符串的ValueOperations
     */
    public ValueOperations<String, Object> getValueOperations() {
        ValueOperations<String, Object> vo = redisTemplate.opsForValue();
        return vo;
    }

    @Override
    public void set(String key, Object value) {
        getValueOperations().set(key, value);
    }

    @Override
    public Object get(String key) {
        Set<String> keys = redisTemplate.keys("*");
        flushdb();
        for (String keyss:keys
             ) {

            System.out.println("key zhi:"+keyss);

        }
        System.out.println("key:"+key);
        System.out.println("value:"+redisTemplate.opsForValue().get(key));
        return redisTemplate.opsForValue().get(key);

//        return getValueOperations().get(key);
    }

    @Override
    public void delete(String key) {
        Set<String> keys = redisTemplate.keys("*");
        for (String keyss:keys
        ) {

            System.out.println("key zhi:"+keyss);
            redisTemplate.delete(keyss);

        }
        RedisOperations<String, Object> operations = getValueOperations().getOperations();
        operations.delete(key);
    }

    public Long lpush(String key, String value) {
        //获取操作list的对象
        ListOperations<String, Object> opsForList = redisTemplate.opsForList();
        Long length = opsForList.leftPush(key, value);
        return length;
    }

    @Override
    public List<Object> range(String key) {
        //获取操作list的对象
        ListOperations<String, Object> opsForList = redisTemplate.opsForList();
        return opsForList.range(key, 0, -1);
    }

    @Override
    public Long lPushAll(String key, List<Object> list) {
        //获取操作list的对象
        ListOperations<String, Object> opsForList = redisTemplate.opsForList();
        return opsForList.leftPushAll(key, list);
    }

    @Override
    public Long remove(String key, long count, Object value) {
        //获取操作list的对象
        ListOperations<String, Object> opsForList = redisTemplate.opsForList();
        return opsForList.remove(key, count, value);
    }

    @Override
    public void set(String key, Object value, Long expire) {
        redisTemplate.opsForValue().set(key, value);
        //设置过期时间
        redisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }
}
