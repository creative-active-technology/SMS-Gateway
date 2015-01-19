/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inkubator.sms.gateway.web;

import com.inkubator.sms.gateway.entity.SmsGatewayUser;
import com.inkubator.sms.gateway.service.UserService;
import com.inkubator.webcore.controller.BaseController;
import com.inkubator.webcore.util.FacesUtil;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author deni
 */
@ManagedBean(name = "userDetailController")
@ViewScoped
public class UserDetailController extends BaseController{

    private SmsGatewayUser selectedUser;
    @ManagedProperty(value = "#{userService}")
    private UserService userService;
    
    @PostConstruct
    @Override
    public void initialization() {
        try {
            super.initialization();
            String userId = FacesUtil.getRequestParameter("execution");
            selectedUser = userService.getEntityByPkWithDetail(Long.parseLong(userId.substring(1)));
        } catch (Exception ex) {
            LOGGER.error("Error", ex);

        }
    }
    
        public String doBack() {
        return "/protected/account/user_view.htm?faces-redirect=true";
    }

    public String doEdit() {
        return "/protected/account/user_form.htm?faces-redirect=true&execution=e" + selectedUser.getId();
    }

    @PreDestroy
    public void cleanAndExit() {
       selectedUser=null;
       userService=null;
    }

    public SmsGatewayUser getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(SmsGatewayUser selectedUser) {
        this.selectedUser = selectedUser;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    
    
}
