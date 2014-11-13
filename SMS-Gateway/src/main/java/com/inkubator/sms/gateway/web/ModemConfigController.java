/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inkubator.sms.gateway.web;

import com.inkubator.sms.gateway.entity.ModemDefinition;
import com.inkubator.sms.gateway.service.ModemDefinitionService;
import com.inkubator.sms.gateway.web.model.ModemDefinitionModel;
import com.inkubator.webcore.controller.BaseController;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Deni Husni FR
 */
@ManagedBean(name = "modemConfigController")
@ViewScoped
public class ModemConfigController extends  BaseController{
    
    @ManagedProperty(value = "#{modemDefinitionService}")
    private ModemDefinitionService modemDefinitionService;
    private List<ModemDefinition>dataModemDefinitions=new ArrayList<>();
    private ModemDefinitionModel modemDefinitionModel;

    public void setModemDefinitionService(ModemDefinitionService modemDefinitionService) {
        this.modemDefinitionService = modemDefinitionService;
    }

    public List<ModemDefinition> getDataModemDefinitions() {
        return dataModemDefinitions;
    }

    public void setDataModemDefinitions(List<ModemDefinition> dataModemDefinitions) {
        this.dataModemDefinitions = dataModemDefinitions;
    }

  

   

    
    @Override
    public void initialization(){
       modemDefinitionModel=new ModemDefinitionModel();
    }
    
    
    public String doRedirectModemConfig(){
        return "/protected/modem_config.htm?faces-redirect=true";
        
    }
}
