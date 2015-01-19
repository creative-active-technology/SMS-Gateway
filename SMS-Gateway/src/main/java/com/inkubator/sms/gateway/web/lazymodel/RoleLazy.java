/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inkubator.sms.gateway.web.lazymodel;

import com.inkubator.sms.gateway.entity.Role;
import com.inkubator.sms.gateway.service.RoleService;
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
 * @author EKA
 */
public class RoleLazy extends LazyDataModel<Role> implements Serializable{
    
    private static final Logger LOGGER = Logger.getLogger(RoleLazy.class);
    private final String parameter;
    private final RoleService roleService;
    private List<Role> listRole = new ArrayList<>();
    private int total;
    
    public RoleLazy(String parameter, RoleService roleService){
        this.parameter = parameter;
        this.roleService = roleService;
    }
    
    @Override
    public List<Role> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters){
        try{
            Order order = null;
            if(sortField != null){
                order = (sortOrder == SortOrder.ASCENDING) ? Order.asc(sortField) : Order.desc(sortField);
            }else{
                order = Order.asc("roleName");
            }
            listRole = roleService.getAllByTextFullService(parameter, first, pageSize, order);
            total = roleService.getTotalByFullTextService(parameter);
            System.out.println(listRole.size());
        } catch (Exception ex){
            LOGGER.error(ex, ex);
        }
        setPageSize(pageSize);
        setRowCount(total);
        return listRole;
    }
    
    @Override
    public Object getRowKey(Role role){
        return role.getId();
    }
    
    @Override
    public Role getRowData(String id){
        for (Role role : listRole){
            if(id.equals(String.valueOf(role.getId()))){
                return role;
            }
        }
        return null;
    }
    
    @Override
    public void setRowIndex(int rowIndex){
        if(rowIndex == -1 || getPageSize() == 0){
            super.setRowIndex(-1);
        }else{
            super.setRowIndex(rowIndex % getPageSize());
        }
    }
}
