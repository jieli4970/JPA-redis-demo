package com.example.jpa.service.impl;

import com.example.jpa.dao.UserJpaDao;
import com.example.jpa.dao.base.BaseDao;
import com.example.jpa.entity.User;
import com.example.jpa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@CacheConfig(cacheNames = "user")//该注解是用来开启声明的类参与缓存,如果方法内的@Cacheable注解没有添加key值，那么会自动使用cahceNames配置参数并且追加方法名。
public class UserServiceImpl implements UserService {
    @Autowired
    private UserJpaDao userJpaDao;

    @Autowired
    private BaseDao baseDao;

    @Override
    public User findByNameAndPasswordAndId(String name, String password, Integer id) {
        return userJpaDao.findByNameAndPasswordAndId(name, password, id);
    }

    @Override
    @CacheEvict(allEntries=true)//移除缓存
    @Async
    public int save(User user) {
        String sql="insert into user(name,password) values(:name,:password);";

        Map<String,Object> param=new HashMap<String,Object>();
        param.put("name",user.getName());
        param.put("password",user.getPassword());
        return baseDao.addOrDelOrUpdateMethod(sql,param);
    }

    @Cacheable//配置方法的缓存参数，可自定义缓存的key以及value。
    @Override
    public List<Map> findAll() {
        String sql="select id,name,password from user";
        return baseDao.commonQueryMethod(sql);
    }

    @Override
    public int updateNameByPassword(String name, String password) {
        return userJpaDao.updateNameByPassword(name, password);
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userJpaDao.findAll(pageable);
    }

    @Override
    public void delete(User entity) {
        userJpaDao.delete(entity);
    }
}
