/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inkubator.sms.gateway.dao;

import com.inkubator.datacore.dao.IDAO;
import com.inkubator.sms.gateway.entity.GroupPhone;
import java.util.List;
import org.hibernate.criterion.Order;

/**
 *
 * @author deni.fahri
 */
public interface GroupPhoneDao extends IDAO<GroupPhone> {

    public List<GroupPhone> getAllByFullTextService(String parameter, int first, int pageSize, Order order);

    public Integer getTotalByFullTextService(String parameter);
}
