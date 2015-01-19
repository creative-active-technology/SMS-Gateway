/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inkubator.sms.gateway.dao;

import com.inkubator.datacore.dao.IDAO;
import com.inkubator.sms.gateway.entity.UserRole;
import java.util.List;

/**
 *
 * @author deni
 */
public interface UserRoleDao extends IDAO<UserRole> {

    List<UserRole> getByUserId(Long id);
}