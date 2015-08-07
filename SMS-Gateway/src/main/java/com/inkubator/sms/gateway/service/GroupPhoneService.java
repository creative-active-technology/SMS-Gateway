/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inkubator.sms.gateway.service;

import com.inkubator.datacore.service.IService;
import com.inkubator.sms.gateway.entity.GroupPhone;
import java.util.List;
import org.hibernate.criterion.Order;

/**
 *
 * @author deni.fahri
 */
public interface GroupPhoneService extends IService<GroupPhone> {

    public List<GroupPhone> getAllByFullTextService(String parameter, int first, int pageSize, Order order) throws Exception;

    public Integer getTotalByFullTextService(String parameter) throws Exception;
}
