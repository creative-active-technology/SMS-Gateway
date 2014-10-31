/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inkubator.sms.gateway.dao.impl;

import com.inkubator.datacore.dao.impl.IDAOImpl;
import com.inkubator.sms.gateway.entity.SmsActivity;
import com.inkubator.sms.gateway.dao.SmsActivityDao;
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

}
