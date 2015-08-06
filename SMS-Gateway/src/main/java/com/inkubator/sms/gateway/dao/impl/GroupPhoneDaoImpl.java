/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inkubator.sms.gateway.dao.impl;

import com.inkubator.datacore.dao.impl.IDAOImpl;
import com.inkubator.sms.gateway.dao.GroupPhoneDao;
import com.inkubator.sms.gateway.entity.GroupPhone;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

/**
 *
 * @author deni.fahri
 */
@Repository(value = "groupPhoneDao")
@Lazy
public class GroupPhoneDaoImpl extends IDAOImpl<GroupPhone> implements GroupPhoneDao {

    @Override
    public Class<GroupPhone> getEntityClass() {
        return GroupPhone.class;
    }

}
