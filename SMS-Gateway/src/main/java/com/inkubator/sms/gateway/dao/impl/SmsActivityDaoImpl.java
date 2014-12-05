/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inkubator.sms.gateway.dao.impl;

import com.inkubator.datacore.dao.impl.IDAOImpl;
import com.inkubator.sms.gateway.dao.SmsActivityDao;
import com.inkubator.sms.gateway.entity.SmsActivity;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Deni Husni FR
 */
@Repository(value = "smsActivityDao")
@Lazy
public class SmsActivityDaoImpl extends IDAOImpl<SmsActivity> implements SmsActivityDao {

    @Override
    public Class<SmsActivity> getEntityClass() {
        return SmsActivity.class;
    }

    @Override
    public List<SmsActivity> getListBySendDate(Date date) {
        Criteria criteria = getCurrentSession().createCriteria(getEntityClass());
        criteria.add(Restrictions.eq("sendDate", new Date()));
        criteria.addOrder(Order.asc("sendDate"));
        criteria.addOrder(Order.desc("sendTime"));
        return criteria.list();
    }

    @Override
     public List<SmsActivity> getAllByLucenSendDate(Date date) {
        FullTextSession fullTextSession = Search.getFullTextSession(getCurrentSession());
        QueryBuilder qb = fullTextSession.getSearchFactory()
                .buildQueryBuilder().forEntity(SmsActivity.class).get();
        org.apache.lucene.search.Query query = qb.keyword().onField("sendDate").matching(date).createQuery();
        org.hibernate.Query hibQuery = fullTextSession.createFullTextQuery(query, SmsActivity.class);
        List<SmsActivity> results = hibQuery.list();
        return results;

    }
}
