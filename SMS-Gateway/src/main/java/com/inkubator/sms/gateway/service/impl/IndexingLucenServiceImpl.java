/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inkubator.sms.gateway.service.impl;

import com.inkubator.sms.gateway.service.IndexingLucenService;
import java.io.Serializable;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Deni Husni FR
 */
//@Service(value = "indexingLucenService")
//@Lazy
public class IndexingLucenServiceImpl implements IndexingLucenService{

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional(readOnly = false,isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW)
    @Override
    public void indexAll() throws Exception {
        System.out.println(" Do Indexingggggg");
        try {
            Session session = sessionFactory.getCurrentSession();

            FullTextSession fullTextSession = Search.getFullTextSession(session);
            fullTextSession.createIndexer().purgeAllOnStart(true)
                    .startAndWait();
            System.out.println(" Do Index Done");  
        } catch (HibernateException | InterruptedException e) {
            throw e;
        }
    }
}
