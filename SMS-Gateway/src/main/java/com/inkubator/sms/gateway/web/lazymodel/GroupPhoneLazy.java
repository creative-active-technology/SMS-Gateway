/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inkubator.sms.gateway.web.lazymodel;

import com.inkubator.sms.gateway.entity.GroupPhone;
import com.inkubator.sms.gateway.entity.TaskDefinition;
import com.inkubator.sms.gateway.service.GroupPhoneService;
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
public class GroupPhoneLazy extends LazyDataModel<GroupPhone> implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(GroupPhoneLazy.class);
    private final String parameter;
    private final GroupPhoneService groupPhoneService;
    private List<GroupPhone> listGroupPhones = new ArrayList<>();
    private int total;

    public GroupPhoneLazy(String paramter, GroupPhoneService groupPhoneService) {
        this.parameter = paramter;
        this.groupPhoneService = groupPhoneService;
    }

    @Override
    public List<GroupPhone> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        try {
            if (sortField != null) {
                if (sortOrder == SortOrder.ASCENDING) {
                    System.out.println(" Order dieksekusi");
                    listGroupPhones = groupPhoneService.getAllByFullTextService(parameter, first, pageSize, Order.asc(sortField));
                } else {
                    listGroupPhones = groupPhoneService.getAllByFullTextService(parameter, first, pageSize, Order.desc(sortField));
                }
            } else {
                listGroupPhones = groupPhoneService.getAllByFullTextService(parameter, first, pageSize, Order.asc("name"));
            }
            total = groupPhoneService.getTotalByFullTextService(parameter);
        } catch (Exception ex) {
            LOGGER.error(ex, ex);
        }
        setPageSize(pageSize);
        setRowCount(total);
        return listGroupPhones;
    }
}
