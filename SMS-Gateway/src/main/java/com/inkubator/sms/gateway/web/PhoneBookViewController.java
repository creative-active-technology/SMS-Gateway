/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inkubator.sms.gateway.web;

import com.inkubator.sms.gateway.entity.GroupPhone;
import com.inkubator.sms.gateway.entity.PhoneBook;
import com.inkubator.sms.gateway.service.GroupPhoneService;
import com.inkubator.sms.gateway.service.PhoneBookService;
import com.inkubator.sms.gateway.web.lazymodel.GroupPhoneLazy;
import com.inkubator.sms.gateway.web.lazymodel.PhoneBookLazy;
import com.inkubator.webcore.controller.BaseController;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author deni.fahri
 */
@ManagedBean(name = "phoneBookViewController")
@ViewScoped
public class PhoneBookViewController extends BaseController {

    @ManagedProperty(value = "#{groupPhoneService}")
    private PhoneBookService phoneBookService;
    private LazyDataModel<PhoneBook> lazyDataModel;
    private String parameter;
    private GroupPhone selecetdGroupPhone;

    @PostConstruct
    @Override
    public void initialization() {
        super.initialization();

    }

    public void setPhoneBookService(PhoneBookService phoneBookService) {
        this.phoneBookService = phoneBookService;
    }

    

    public LazyDataModel<PhoneBook> getLazyDataModel() {
        if (lazyDataModel == null) {
            lazyDataModel = new PhoneBookLazy(parameter, phoneBookService);
        }
        return lazyDataModel;
    }

    public void setLazyDataModel(LazyDataModel<PhoneBook> lazyDataModel) {
        this.lazyDataModel = lazyDataModel;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }
    
    
    public void doSearch(){
        lazyDataModel=null;
    }
    
    public void doAdd(){
        
    }
    
    public void doDetail(){
        
    }
    
    public void doEdit(){
        
    }

    public GroupPhone getSelecetdGroupPhone() {
        return selecetdGroupPhone;
    }

    public void setSelecetdGroupPhone(GroupPhone selecetdGroupPhone) {
        this.selecetdGroupPhone = selecetdGroupPhone;
    }
    
    
}
