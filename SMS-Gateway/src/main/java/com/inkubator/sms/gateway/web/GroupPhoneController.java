/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inkubator.sms.gateway.web;

import com.inkubator.sms.gateway.service.GroupPhoneService;
import com.inkubator.sms.gateway.service.ModemDefinitionService;
import com.inkubator.webcore.controller.BaseController;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author deni.fahri
 */
@ManagedBean(name = "groupPhoneController")
@ViewScoped
public class GroupPhoneController extends BaseController {

    @ManagedProperty(value = "#{groupPhoneService}")
    private GroupPhoneService groupPhoneService;

    @PostConstruct
    @Override
    public void initialization() {
        super.initialization();
    }

    public void setGroupPhoneService(GroupPhoneService groupPhoneService) {
        this.groupPhoneService = groupPhoneService;
    }

}
