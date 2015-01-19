/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inkubator.sms.gateway.web;

import com.inkubator.sms.gateway.SMSGATEWAY;
import com.inkubator.sms.gateway.entity.SmsGatewayUser;
import com.inkubator.sms.gateway.service.UserService;
import com.inkubator.sms.gateway.web.lazymodel.UserLazy;
import com.inkubator.webcore.controller.BaseController;
import com.inkubator.webcore.util.FacesUtil;
import com.inkubator.webcore.util.MessagesResourceUtil;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.model.LazyDataModel;
import org.springframework.dao.DataIntegrityViolationException;

/**
 *
 * @author deni
 */
@ManagedBean(name = "userViewController")
@ViewScoped
public class UserViewController extends BaseController {

    @ManagedProperty(value = "#{userService}")
    private UserService userService;
    private LazyDataModel<SmsGatewayUser> lazyDataModel;
    private String parameter;
    private SmsGatewayUser selectedUser;

    @Override
    public void initialization() {
        super.initialization();
    }

    public void doSearch() {
        lazyDataModel = null;
    }

    public String doAdd() {
        return "/protected/account/user_form.htm?faces-redirect=true";
    }

    public String doUpdate() {
        return "/protected/account/user_form.htm?faces-redirect=true&execution=e" + selectedUser.getId();
    }
        
    public String doDetail() {
        return "/protected/account/user_detail.htm?faces-redirect=true&execution=e" + selectedUser.getId();
    }
    
    public void doDelete() {
        try {
            this.userService.delete(selectedUser);
            MessagesResourceUtil.setMessages(FacesMessage.SEVERITY_INFO, "global.delete", "global.delete_successfully",
                    FacesUtil.getSessionAttribute(SMSGATEWAY.BAHASA_ACTIVE).toString());

        } catch (ConstraintViolationException | DataIntegrityViolationException ex) {
            MessagesResourceUtil.setMessages(FacesMessage.SEVERITY_ERROR, "global.error", "error.delete_constraint",
                    FacesUtil.getSessionAttribute(SMSGATEWAY.BAHASA_ACTIVE).toString());
            LOGGER.error("Error", ex);
        } catch (Exception ex) {
            LOGGER.error("Error", ex);
        }
    }
    
    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public LazyDataModel<SmsGatewayUser> getLazyDataModel() {
        if (lazyDataModel == null) {
            lazyDataModel = new UserLazy(parameter, userService);
        }
        return lazyDataModel;
    }

    public void setLazyDataModel(LazyDataModel<SmsGatewayUser> lazyDataModel) {
        this.lazyDataModel = lazyDataModel;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public SmsGatewayUser getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(SmsGatewayUser selectedUser) {
        this.selectedUser = selectedUser;
    }

}
