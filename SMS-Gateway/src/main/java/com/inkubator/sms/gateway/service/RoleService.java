/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inkubator.sms.gateway.service;

import com.inkubator.datacore.service.IService;
import com.inkubator.sms.gateway.entity.Role;
import java.util.List;
import org.hibernate.criterion.Order;

/**
 *
 * @author EKA
 */
public interface RoleService extends IService<Role> { 
    
    public List<Role> getAllByTextFullService(String parameter, int minResult, int maxResult, Order order);
    
    public Integer getTotalByFullTextService(String parameter);
    
}
