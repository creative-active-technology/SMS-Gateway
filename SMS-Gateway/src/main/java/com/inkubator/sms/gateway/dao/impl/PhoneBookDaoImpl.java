/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inkubator.sms.gateway.dao.impl;

import com.inkubator.datacore.dao.impl.IDAOImpl;
import com.inkubator.sms.gateway.dao.PhoneBookDao;
import com.inkubator.sms.gateway.entity.PhoneBook;
import java.util.List;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.SearchFactory;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

/**
 *
 * @author deni.fahri
 */
@Repository(value = "phoneBookDao")
@Lazy
public class PhoneBookDaoImpl extends IDAOImpl<PhoneBook> implements PhoneBookDao {

    @Override
    public Class<PhoneBook> getEntityClass() {
        return PhoneBook.class;
    }

    @Override
    public List<PhoneBook> getAllByFullTextService(String parameter, int first, int pageSize, Order order) {
        FullTextSession fullTextSession = Search.getFullTextSession(getCurrentSession());
        Criteria criteria = fullTextSession.createCriteria(getEntityClass());
        Sort sort;
        if (order.isAscending()) {
            sort = new Sort(new SortField(order.getPropertyName(), SortField.STRING_VAL));
        } else {
            sort = new Sort(new SortField(order.getPropertyName(), SortField.STRING_VAL, true));
        }
        FullTextQuery fullTextQuery1 = doSearchFullText(parameter, fullTextSession);
        fullTextQuery1.setCriteriaQuery(criteria);
        fullTextQuery1.setFirstResult(first);
        fullTextQuery1.setMaxResults(pageSize);
        fullTextQuery1.setSort(sort);
        return fullTextQuery1.list();
    }

    @Override
    public Integer getTotalByFullTextService(String parameter) {
        FullTextSession fullTextSession = Search.getFullTextSession(getCurrentSession());
        return doSearchFullText(parameter, fullTextSession).getResultSize();
    }

    private FullTextQuery doSearchFullText(String parameter, FullTextSession fullTextSession) {

        SearchFactory searchFactory = fullTextSession.getSearchFactory();
        QueryBuilder mythQB = searchFactory.buildQueryBuilder().forEntity(getEntityClass()).get();
        Query luceneQuery;
        if (parameter != null && !parameter.equalsIgnoreCase("")) {
            luceneQuery = mythQB.keyword().onField("name").boostedTo(3)
                    .andField("mobilePhone")
                    .andField("officePhone")
                    .andField("emailAddress")
                    .matching(parameter + "*").createQuery();
        } else {
            luceneQuery = mythQB.all().createQuery();
        }
        FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery(luceneQuery, getEntityClass());
        return fullTextQuery;
    }

}
