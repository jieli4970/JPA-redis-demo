package com.example.jpa.dao.base.impl;

import com.example.jpa.dao.base.BaseDao;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;
/**
 * 类说明:BaseDao实现类
 * @author
 * @version
 */
@Repository
public class BaseDaoImpl implements BaseDao {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 获取hibernate Session
     * @return
     */
    private Session getHibernateSession(){
        Session hibernateSession=entityManager.unwrap(org.hibernate.Session.class);
        return hibernateSession;
    }

    /**
     * 公共查询方法【手动拼接参数到SQL中】
     * @param sql
     */
    @Override
    public List<Map> commonQueryMethod(String sql) {
        //执行SQL查询【设置返回结果为Map】
        Query result = getHibernateSession().createSQLQuery(sql).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        return result.list();
    }

    /**
     * 增删改共用方法【手动拼接参数到SQL中】
     * @param sql
     */
    @Override
    public int addOrDelOrUpdateMethod(String sql) {
        Query result = getHibernateSession().createSQLQuery(sql);
        int executeUpdate = result.executeUpdate();
        return executeUpdate;
    }

    /**
     * 公共查询方法【传递参数集合自动绑定参数】参数集合中的key要和SQL中的命名参数名称一致
     * select * from xx where id = :key
     * put("key",'1')
     * @return
     */
    @Override
    public List<Map> commonQueryMethod(String sql, Map<String, Object> param) {
        Query result = getHibernateSession().createSQLQuery(sql).setProperties(param).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        return result.list();
    }

    /**
     * 增删改共用方法【传递参数集合自动绑定参数】参数集合中的key要和SQL中的命名参数名称一致
     * @param sql
     */
    @Override
    public int addOrDelOrUpdateMethod(String sql, Map<String, Object> param) {
        Query result = getHibernateSession().createSQLQuery(sql).setProperties(param);
        int executeUpdate = result.executeUpdate();
        return executeUpdate;
    }
}
