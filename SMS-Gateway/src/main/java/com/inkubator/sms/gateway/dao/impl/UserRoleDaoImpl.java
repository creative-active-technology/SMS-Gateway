/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inkubator.sms.gateway.dao.impl;

import com.inkubator.datacore.dao.impl.IDAOImpl;
import com.inkubator.sms.gateway.dao.UserRoleDao;
import com.inkubator.sms.gateway.entity.UserRole;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

/**
 *
 * @author deni
 */
@Repository(value = "userRoleDao")
@Lazy
public class UserRoleDaoImpl extends IDAOImpl<UserRole> implements UserRoleDao {

    @Override
    public Class<UserRole> getEntityClass() {
        return UserRole.class;
    }

    @Override
    public List<UserRole> getByUserId(Long id) {
        Criteria criteria = getCurrentSession().createCriteria(getEntityClass());
        criteria.createAlias("user", "user");
        criteria.add(Restrictions.eq("user.id", id));
        criteria.addOrder(Order.desc("role"));
        criteria.setFetchMode("role", FetchMode.JOIN);
        return criteria.list();
    }
    
}
