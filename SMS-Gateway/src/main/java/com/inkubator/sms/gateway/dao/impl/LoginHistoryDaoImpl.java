/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inkubator.sms.gateway.dao.impl;

import com.inkubator.datacore.dao.impl.IDAOImpl;
import com.inkubator.sms.gateway.dao.LoginHistoryDao;
import com.inkubator.sms.gateway.entity.LoginHistory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Deni Husni FR
 */
@Repository(value = "loginHistoryDao")
@Lazy
public class LoginHistoryDaoImpl extends IDAOImpl<LoginHistory> implements LoginHistoryDao{
    private static final long serialVersionUID = -3838059330383642763L;

    @Override
    public Class<LoginHistory> getEntityClass() {
        return LoginHistory.class;
    }

}
