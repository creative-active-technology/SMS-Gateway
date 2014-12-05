/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inkubator.sms.gateway.dao.impl;

import com.inkubator.datacore.dao.impl.IDAOImpl;
import com.inkubator.sms.gateway.dao.ModemDefinitionDao;
import com.inkubator.sms.gateway.entity.ModemDefinition;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Deni Husni FR
 */
@Repository(value = "modemDefinitionDao")
@Lazy
public class ModemDefinitionDaoImpl extends IDAOImpl<ModemDefinition> implements ModemDefinitionDao {

    @Override
    public Class<ModemDefinition> getEntityClass() {
        return ModemDefinition.class;
    }

    @Override
    public Long getTotalByModemId(String modemId) {
        Criteria criteria = getCurrentSession().createCriteria(getEntityClass());
        criteria.add(Restrictions.eq("modemId", modemId));
        return (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
    }

    @Override
    public long getTotalByModemIdAndNotId(String modemId, Long id) {
        Criteria criteria = getCurrentSession().createCriteria(getEntityClass());
        criteria.add(Restrictions.like("modemId", modemId, MatchMode.ANYWHERE));
        criteria.add(Restrictions.ne("id", id));
        return (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
    }

}
