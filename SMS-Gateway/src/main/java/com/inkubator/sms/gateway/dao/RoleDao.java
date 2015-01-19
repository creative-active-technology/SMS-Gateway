/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inkubator.sms.gateway.dao;

import com.inkubator.datacore.dao.IDAO;
import com.inkubator.sms.gateway.entity.Role;
import java.util.List;
import org.hibernate.criterion.Order;

/**
 *
 * @author EKA
 */
public interface RoleDao extends IDAO<Role>{
    public List<Role> getAllByFullTextService(String parameter, int minResult, int maxResult, Order order);
    
    public Integer getTotalByFullTextService(String parameter);
    
}
