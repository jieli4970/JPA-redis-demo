package com.example.jpa.dao.base;

import java.util.List;
import java.util.Map;

/**
 * jpa公共增删改查
 */
public interface BaseDao {
    /**
     * 公共查询方法【手动进行参数的拼接】
     * @return
     */
    List<Map> commonQueryMethod(String sql);

    /**
     * 增删改共用方法【手动进行参数的拼接】
     * @param sql
     */
    int addOrDelOrUpdateMethod(String sql);

    /**
     * 公共查询方法【传递参数集合自动绑定参数】参数集合中的key要和SQL中的命名参数名称一致
     * @return
     */
    List<Map> commonQueryMethod(String sql,Map<String,Object> param);

    /**
     * 增删改共用方法【传递参数集合自动绑定参数】参数集合中的key要和SQL中的命名参数名称一致
     * @param sql
     */
    int addOrDelOrUpdateMethod(String sql,Map<String,Object> param);
}
