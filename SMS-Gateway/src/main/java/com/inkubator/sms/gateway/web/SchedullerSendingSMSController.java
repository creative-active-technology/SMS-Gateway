/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inkubator.sms.gateway.web;

import com.inkubator.sms.gateway.web.model.SchedullerSmsModel;
import com.inkubator.webcore.controller.BaseController;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Deni Husni FR
 */
@ManagedBean(name = "schedullerSendingSMSController")
@ViewScoped
public class SchedullerSendingSMSController extends BaseController {
private SchedullerSmsModel schedullerSmsModel;
    
    @Override
    public void initialization() {
        super.initialization();
        schedullerSmsModel=new SchedullerSmsModel();
    }

   
}
