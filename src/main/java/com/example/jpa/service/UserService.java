package com.example.jpa.service;

import com.example.jpa.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;

public interface UserService {
    User findByNameAndPasswordAndId(String name,String password,Integer id);

    int save(User user);

    List<Map> findAll();

    int updateNameByPassword(String name, String password);

    //Page<User2> findAll(PageRequest pageRequest);

    Page<User> findAll(Pageable pageable);

    //删除用户
    void delete(User entity);
}
