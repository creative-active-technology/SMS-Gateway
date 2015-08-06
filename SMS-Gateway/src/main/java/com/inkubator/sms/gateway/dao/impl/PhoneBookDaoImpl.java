/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inkubator.sms.gateway.dao.impl;

import com.inkubator.datacore.dao.impl.IDAOImpl;
import com.inkubator.sms.gateway.dao.PhoneBookDao;
import com.inkubator.sms.gateway.entity.PhoneBook;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

/**
 *
 * @author deni.fahri
 */
@Repository(value = "phoneBookDao")
@Lazy
public class PhoneBookDaoImpl extends IDAOImpl<PhoneBook> implements PhoneBookDao {

    @Override
    public Class<PhoneBook> getEntityClass() {
        return PhoneBook.class;
    }

}
