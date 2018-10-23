package com.example.jpa.controller;

import com.alibaba.fastjson.JSON;
import com.example.jpa.entity.User;
import com.example.jpa.service.UserService;
import com.example.jpa.service.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RedisService redisService;

    @RequestMapping("/findall")
    public String findAll(Model model){
        List<Map> data=userService.findAll();
        model.addAttribute("DATA",data);
        return "index";
    }

    @RequestMapping("/save")
    public String saveUser(){
        User user=new User("zhangsan","123");
        userService.save(user);
        return "redirect:/findall";
    }

    /**
     * 根据key 获取缓存
     * @return
     */
    @RequestMapping("/get")
    @ResponseBody
    public String getredis(String key){
       Object obj= redisService.get(key);
        System.out.println(obj);
       String red= JSON.toJSONString(obj);
        return red;
    }

    /**
     * 根据key 添加缓存
     * @return
     */
    @RequestMapping("/set")
    @ResponseBody
    public String setredis(){
        User user=new User();
        user.setId(100000001);
        user.setName("hhh1");
//        user.setPassword("123456");

        User user2=new User();
        user2.setId(100000002);
        user2.setName("hhhh2");
//        user2.setPassword("223456");

        List<User> data=new ArrayList<User>();
        data.add(user);
        data.add(user2);

        redisService.set("keys1",data,100000L);
        return "set redis success";
    }

    /**
     * 根据key 删除缓存
     * @return
     */
    @RequestMapping("/del")
    @ResponseBody
    public String delredis(String key){
        redisService.delete(key);
        return "delete redis success";
    }

    /**
     *
     * @return
     */
    @RequestMapping("/lpush")
    @ResponseBody
    public Long lpushredis(){
        Long size=redisService.lpush("keys1","{\"id\":100000002,\"name\":\"哈哈哈2\",\"password\":\"123456\"}");
        return size;
    }

    @RequestMapping("/range")
    @ResponseBody
    public List<Object> rangeredis(){
        List<Object> data= redisService.range("keys1");
        return data;
    }

}
