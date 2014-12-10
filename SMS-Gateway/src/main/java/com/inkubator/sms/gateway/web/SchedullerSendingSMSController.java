/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inkubator.sms.gateway.web;

import com.inkubator.sms.gateway.entity.ModemDefinition;
import com.inkubator.sms.gateway.entity.TaskDefinition;
import com.inkubator.sms.gateway.service.TaskDefinitionService;
import com.inkubator.sms.gateway.web.lazymodel.TaskDefinitionLazy;
import com.inkubator.sms.gateway.web.model.SchedullerSmsModel;
import com.inkubator.webcore.controller.BaseController;
import com.inkubator.webcore.util.FacesUtil;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author Deni Husni FR
 */
@ManagedBean(name = "schedullerSendingSMSController")
@ViewScoped
public class SchedullerSendingSMSController extends BaseController {

    private SchedullerSmsModel schedullerSmsModel;
    @ManagedProperty(value = "#{taskDefinitionService}")
    private TaskDefinitionService taskDefinitionService;
    private Boolean isDisable;
    private LazyDataModel<TaskDefinition> lazyDataModel;
    private String pencarian;

    @Override
    public void initialization() {
        try {
            schedullerSmsModel = new SchedullerSmsModel();
            schedullerSmsModel.setRepeatTime(0);
            schedullerSmsModel.setFromSending("System");
            isDisable = Boolean.FALSE;
//            List<TaskDefinition> dataToShow = taskDefinitionService.getAllByFullTextService(null, 0, 2, null);
//            System.out.println(" Jumlah Data " + dataToShow.size());
        } catch (Exception ex) {
            LOGGER.error(ex, ex);
        }
    }

    public SchedullerSmsModel getSchedullerSmsModel() {
        return schedullerSmsModel;
    }

    public void setSchedullerSmsModel(SchedullerSmsModel schedullerSmsModel) {
        this.schedullerSmsModel = schedullerSmsModel;
    }

    public void saveSchedule() {
        if (schedullerSmsModel.getSendDate().equals(new Date()) || schedullerSmsModel.getSendDate().before(new Date())) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Proses Simpan", "Tanggal pengiriman tidak boleh hari Ini atau kemarin");
            FacesUtil.getFacesContext().addMessage(null, msg);
        } else {
            TaskDefinition definition = getEntityFromModel(schedullerSmsModel);
            try {
                this.taskDefinitionService.save(definition);
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Proses Simpan", "Schedule pengiriman sms berhasil disimpan");
                FacesUtil.getFacesContext().addMessage(null, msg);
                initialization();
            } catch (Exception ex) {
                LOGGER.error(ex, ex);
            }
        }

    }

    public void doReset() {
        schedullerSmsModel = new SchedullerSmsModel();

    }

    public void doAdd() {
        schedullerSmsModel.getListPhone().add(schedullerSmsModel.getDestination());
        if (schedullerSmsModel.getListPhoneAsString() != null) {
            schedullerSmsModel.setListPhoneAsString(schedullerSmsModel.getListPhoneAsString() + ";" + schedullerSmsModel.getDestination());
        } else {
            schedullerSmsModel.setListPhoneAsString(schedullerSmsModel.getDestination());
        }
        schedullerSmsModel.setDestination("");
    }

    public void setTaskDefinitionService(TaskDefinitionService taskDefinitionService) {
        this.taskDefinitionService = taskDefinitionService;
    }

    public TaskDefinition getEntityFromModel(SchedullerSmsModel model) {
        TaskDefinition definition = new TaskDefinition();
        if (model.getId() != null) {
            definition.setId(model.getId());
        }
        definition.setDate(model.getSendDate());
        definition.setDestination(model.getListPhoneAsString());// for multi destination
        definition.setFromSending(model.getFromSending());
        definition.setIsRepeatOnCondition(model.getIsRepeatOnCondition());
        definition.setModemDefinition(new ModemDefinition(model.getModemId()));
        definition.setName(model.getName());
        definition.setRepeatTime(model.getRepeatTime());
        definition.setScheduleType(model.getScheduleType());
        definition.setSmsContent(model.getSmsContent());
        definition.setTime(model.getSendTime());
        return definition;

    }

    public Boolean getIsDisable() {
        return isDisable;
    }

    public void setIsDisable(Boolean isDisable) {
        this.isDisable = isDisable;
    }

    public void changeScheduleType() {
        if (schedullerSmsModel.getScheduleType().equalsIgnoreCase("oneTime")) {
            isDisable = Boolean.TRUE;
            schedullerSmsModel.setIsRepeatOnCondition("ONETIME");
        } else {
            isDisable = Boolean.FALSE;
            schedullerSmsModel.setIsRepeatOnCondition("");
        }

    }

    public LazyDataModel<TaskDefinition> getLazyDataModel() {
        if (lazyDataModel == null) {
            lazyDataModel = new TaskDefinitionLazy(pencarian, taskDefinitionService);
        }
        return lazyDataModel;
    }

    public void setLazyDataModel(LazyDataModel<TaskDefinition> lazyDataModel) {
        this.lazyDataModel = lazyDataModel;
    }

    public String getPencarian() {
        return pencarian;
    }

    public void setPencarian(String pencarian) {
        this.pencarian = pencarian;
    }

    public void doSearch(){
        lazyDataModel=null;
    }
}
