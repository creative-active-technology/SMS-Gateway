/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inkubator.sms.gateway.web.lazymodel;

import com.inkubator.sms.gateway.entity.GroupPhone;
import com.inkubator.sms.gateway.entity.PhoneBook;
import com.inkubator.sms.gateway.service.PhoneBookService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Order;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author deni.fahri
 */
public class PhoneBookLazy extends LazyDataModel<PhoneBook> implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(PhoneBookLazy.class);
    private final String parameter;
    private final PhoneBookService phoneBookService;
    private List<PhoneBook> listGroupPhones = new ArrayList<>();
    private int total;

    public PhoneBookLazy(String parameter, PhoneBookService phoneBookService) {
        this.parameter = parameter;
        this.phoneBookService = phoneBookService;
    }

    
    @Override
    public List<PhoneBook> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        try {
            if (sortField != null) {
                if (sortOrder == SortOrder.ASCENDING) {
                    System.out.println(" Order dieksekusi");
                    listGroupPhones = phoneBookService.getAllByFullTextService(parameter, first, pageSize, Order.asc(sortField));
                } else {
                    listGroupPhones = phoneBookService.getAllByFullTextService(parameter, first, pageSize, Order.desc(sortField));
                }
            } else {
                listGroupPhones = phoneBookService.getAllByFullTextService(parameter, first, pageSize, Order.asc("name"));
            }
            total = phoneBookService.getTotalByFullTextService(parameter);
        } catch (Exception ex) {
            LOGGER.error(ex, ex);
        }
        setPageSize(pageSize);
        setRowCount(total);
        return listGroupPhones;
    }
}
