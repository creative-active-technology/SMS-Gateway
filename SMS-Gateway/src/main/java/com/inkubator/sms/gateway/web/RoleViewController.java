/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inkubator.sms.gateway.web;

import com.inkubator.sms.gateway.entity.Role;
import com.inkubator.sms.gateway.service.RoleService;
import com.inkubator.sms.gateway.web.lazymodel.RoleLazy;
import com.inkubator.webcore.controller.BaseController;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author EKA
 */
@ManagedBean(name = "roleViewController")
@ViewScoped
public class RoleViewController extends BaseController{
    
    @ManagedProperty(value = "#{roleService}")
    private RoleService roleService;
    private LazyDataModel<Role> lazyDataModel;
    private String parameter;
    private Role selectedRole;
    
    @Override
    public void initialization(){
        super.initialization();
    }
    
    public void doSearch(){
        lazyDataModel = null;
    }

    public RoleService getRoleService() {
        return roleService;
    }

    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    public LazyDataModel<Role> getLazyDataModel() {
        if(lazyDataModel == null){
            lazyDataModel = new RoleLazy(parameter, roleService);
        }
        return lazyDataModel;
    }

    public void setLazyDataModel(LazyDataModel<Role> lazyDataModel) {
        this.lazyDataModel = lazyDataModel;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public Role getSelectedRole() {
        return selectedRole;
    }

    public void setSelectedRole(Role selectedRole) {
        this.selectedRole = selectedRole;
    }
            
    
    
}
    