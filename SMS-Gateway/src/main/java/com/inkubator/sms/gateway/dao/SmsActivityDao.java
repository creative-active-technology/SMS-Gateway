/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inkubator.sms.gateway.dao;

import com.inkubator.datacore.dao.IDAO;
import com.inkubator.sms.gateway.entity.SmsActivity;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Deni Husni FR
 */
public interface SmsActivityDao extends IDAO<SmsActivity> {

    public List<SmsActivity> getListBySendDate(Date date);

    public List<SmsActivity> getAllByLucenSendDate(Date date);
}
