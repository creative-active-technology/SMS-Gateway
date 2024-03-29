/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inkubator.sms.gateway.service;

import com.inkubator.datacore.service.IService;
import com.inkubator.sms.gateway.entity.SmsGatewayUser;
import java.util.List;
import org.hibernate.criterion.Order;

/**
 *
 * @author deni
 */
public interface UserService extends IService<SmsGatewayUser> {

    public List<SmsGatewayUser> getAllByFullTextService(String parameter, int minResult, int maxResult, Order order) throws Exception;

    public Integer getTotalByFullTextService(String parameter) throws Exception;
    
    public SmsGatewayUser getEntityByPkWithDetail(Long id) throws Exception;
    
    public void saveAndNotification(SmsGatewayUser user) throws Exception;
    
    public SmsGatewayUser getByEmailAddressInNotLock(String emailAddress) throws Exception;
    
    public void resetPassword(SmsGatewayUser user) throws Exception;
}
