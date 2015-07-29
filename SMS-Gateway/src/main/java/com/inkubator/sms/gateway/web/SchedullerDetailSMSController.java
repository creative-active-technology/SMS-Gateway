/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inkubator.sms.gateway.web;

import com.inkubator.common.util.JsonConverter;
import com.inkubator.sms.gateway.entity.ModemDefinition;
import com.inkubator.sms.gateway.entity.TaskDefinition;
import com.inkubator.sms.gateway.service.TaskDefinitionService;
import com.inkubator.sms.gateway.web.lazymodel.TaskDefinitionLazy;
import com.inkubator.sms.gateway.web.model.SchedullerSmsModel;
import com.inkubator.webcore.controller.BaseController;
import com.inkubator.webcore.util.FacesUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author Deni Husni FR
 */
@ManagedBean(name = "schedullerDetailSMSController")
@ViewScoped
public class SchedullerDetailSMSController extends BaseController {

    @ManagedProperty(value = "#{taskDefinitionService}")
    private TaskDefinitionService taskDefinitionService;
    @ManagedProperty(value = "#{jsonGsonConverter}")
    private JsonConverter jsonGsonConverter;
    private TaskDefinition selectedTaskDefinition;

    @Override
    public void initialization() {
        try {
            super.initialization();
            String id = FacesUtil.getRequestParameter("execution");
            selectedTaskDefinition = taskDefinitionService.getEntiyByPK(Long.parseLong(id.substring(1)));
            
        } catch (Exception ex) {
            LOGGER.error(ex, ex);
        }
    }

    public void setTaskDefinitionService(TaskDefinitionService taskDefinitionService) {
        this.taskDefinitionService = taskDefinitionService;
    }

    public void setJsonGsonConverter(JsonConverter jsonGsonConverter) {
        this.jsonGsonConverter = jsonGsonConverter;
    }

    public TaskDefinition getSelectedTaskDefinition() {
        return selectedTaskDefinition;
    }

    public void setSelectedTaskDefinition(TaskDefinition selectedTaskDefinition) {
        this.selectedTaskDefinition = selectedTaskDefinition;
    }

    
}
