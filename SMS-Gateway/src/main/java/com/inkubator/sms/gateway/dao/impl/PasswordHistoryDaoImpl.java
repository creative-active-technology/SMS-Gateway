/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inkubator.sms.gateway.dao.impl;

import com.inkubator.datacore.dao.impl.IDAOImpl;
import com.inkubator.sms.gateway.dao.PasswordHistoryDao;
import com.inkubator.sms.gateway.entity.PasswordHistory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

/**
 *
 * @author deni
 */
@Repository(value = "passwordHistoryDao")
@Lazy
public class PasswordHistoryDaoImpl extends IDAOImpl<PasswordHistory> implements PasswordHistoryDao{

    @Override
    public Class<PasswordHistory> getEntityClass() {
        return PasswordHistory.class;
    }
    
}
