/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inkubator.sms.gateway.web;

import com.inkubator.sms.gateway.entity.SmsActivity;
import com.inkubator.sms.gateway.service.SmsActivityService;
import com.inkubator.webcore.controller.BaseController;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Deni Husni FR
 */
@ManagedBean(name = "homeController")
@ViewScoped
public class HomeController extends BaseController {

    @ManagedProperty(value = "#{smsActivityService}")
    private SmsActivityService smsActivityService;
    private List<SmsActivity> dataTableSMSActivities = new ArrayList<>();

    public void setSmsActivityService(SmsActivityService smsActivityService) {
        this.smsActivityService = smsActivityService;
    }

    public List<SmsActivity> getDataTableSMSActivities() {
        return dataTableSMSActivities;
    }

    public void setDataTableSMSActivities(List<SmsActivity> dataTableSMSActivities) {
        this.dataTableSMSActivities = dataTableSMSActivities;
    }

    @Override
    public void initialization() {
        try {
            dataTableSMSActivities = smsActivityService.getListBySendDate(new Date());
        } catch (Exception ex) {
            LOGGER.error(ex, ex);
        }
    }

    public String doRedirectModemConfig() {
        return "/protected/modem_config.htm?faces-redirect=true";

    }

    public String doRedirectSms() {
        return "/protected/sending_sms.htm?faces-redirect=true";
    }
}
