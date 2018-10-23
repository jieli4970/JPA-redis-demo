package com.example.jpa.dao;

import com.example.jpa.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserJpaDao extends JpaRepository<User,Long> {
    /*
     * Jpa命名规范，查询
     */
    User findByNameAndPasswordAndId(String name,String password,Integer id);
    /*
     * (non-Javadoc)新增用户
     * @see org.springframework.data.repository.CrudRepository#save(S)
     */
    User save(User user);
    //查询全部
    List<User> findAll();

    //分页查询
    //Page<User2> findAll(PageRequest pageRequest);
    Page<User> findAll(Pageable pageable);

    @Modifying
    @Query("update User  as c set c.name = ?1 where c.password=?2")
    int updateNameByPassword(String name, String password);

    void delete(User entity);
}
