/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inkubator.sms.gateway.web;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.inkubator.common.util.JsonConverter;
import com.inkubator.sms.gateway.entity.ModemDefinition;
import com.inkubator.sms.gateway.entity.TaskDefinition;
import com.inkubator.sms.gateway.service.ModemDefinitionService;
import com.inkubator.sms.gateway.service.TaskDefinitionService;
import com.inkubator.sms.gateway.web.lazymodel.TaskDefinitionLazy;
import com.inkubator.sms.gateway.web.model.SchedullerSmsModel;
import com.inkubator.webcore.controller.BaseController;
import com.inkubator.webcore.util.FacesUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
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
//    private TaskDefinition selectedTaskDefinition;
    @ManagedProperty(value = "#{jsonGsonConverter}")
    private JsonConverter jsonGsonConverter;
    private Map<String, Long> mapOfModem = new HashMap<>();
    private List<ModemDefinition> listModemDefinitions = new ArrayList<>();
    @ManagedProperty(value = "#{modemDefinitionService}")
    private ModemDefinitionService modemDefinitionService;
    private Boolean isEdit;

    @Override
    public void initialization() {
        try {
            super.initialization();
            String id = FacesUtil.getRequestParameter("execution");
            listModemDefinitions = this.modemDefinitionService.getAllData();
            for (ModemDefinition definition : listModemDefinitions) {
                mapOfModem.put(definition.getModemId() + "-" + definition.getManufacture(), definition.getId());
            }
            if (id != null) {
                TaskDefinition selectedTaskDefinition = taskDefinitionService.getEntiyByPK(Long.parseLong(id.substring(1)));
                schedullerSmsModel = new SchedullerSmsModel();
                schedullerSmsModel.setId(selectedTaskDefinition.getId());
                schedullerSmsModel.setDestination(selectedTaskDefinition.getDestination());
                schedullerSmsModel.setModemId(selectedTaskDefinition.getModemDefinition().getId());
                schedullerSmsModel.setFromSending(selectedTaskDefinition.getFromSending());
                schedullerSmsModel.setIsRepeatOnCondition(selectedTaskDefinition.getIsRepeatOnCondition());
                schedullerSmsModel.setSendDate(selectedTaskDefinition.getDate());
                schedullerSmsModel.setSendTime(selectedTaskDefinition.getTime());
                schedullerSmsModel.setName(selectedTaskDefinition.getName());
                schedullerSmsModel.setRepeatTime(selectedTaskDefinition.getRepeatTime());
                schedullerSmsModel.setSmsContent(selectedTaskDefinition.getSmsContent());
                schedullerSmsModel.setScheduleType(selectedTaskDefinition.getScheduleType());
               
                Gson gson = new GsonBuilder().create();
                TypeToken<List<String>> token = new TypeToken<List<String>>() {
                };
                List<String> toLoop = gson.fromJson(selectedTaskDefinition.getDestination(), token.getType());
                schedullerSmsModel.setListPhone(toLoop);
                for (String toLoop1 : toLoop) {
                    if (schedullerSmsModel.getListPhoneAsString() != null) {
                        schedullerSmsModel.setListPhoneAsString(schedullerSmsModel.getListPhoneAsString() + ";" + toLoop1);
                    } else {
                        schedullerSmsModel.setListPhoneAsString(toLoop1);
                    }
                }
                schedullerSmsModel.setDestination("");
                isEdit = Boolean.TRUE;
            } else {
                schedullerSmsModel = new SchedullerSmsModel();
                schedullerSmsModel.setRepeatTime(1);
//            schedullerSmsModel.setFromSending("System");
                isDisable = Boolean.FALSE;
                isEdit = Boolean.FALSE;
            }

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

    public String saveSchedule() {
        System.out.println(" Tanggaal kirimnya " + schedullerSmsModel.getSendDate());
        if (schedullerSmsModel.getSendDate().equals(new Date()) || schedullerSmsModel.getSendDate().before(new Date())) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Proses Simpan", "Tanggal pengiriman tidak boleh hari Ini atau kemarin");
            FacesUtil.getFacesContext().addMessage(null, msg);
        } else {
            TaskDefinition definition = getEntityFromModel(schedullerSmsModel);
            try {
                if (isEdit) {
                    this.taskDefinitionService.update(definition);
                } else {
                    this.taskDefinitionService.save(definition);
                }
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Proses Simpan", "Schedule pengiriman sms berhasil disimpan");
                FacesUtil.getFacesContext().addMessage(null, msg);
                FacesUtil.getExternalContext().getFlash().setKeepMessages(true);
                initialization();
                return "/protected/scheduller_sms_view.htm?faces-redirect=true";

            } catch (Exception ex) {
                LOGGER.error(ex, ex);
            }
        }

        return null;
    }

    public void doReset() {
        schedullerSmsModel = new SchedullerSmsModel();
        schedullerSmsModel.setRepeatTime(0);

    }

    public void doAdd() {
        schedullerSmsModel.getListPhone().add(schedullerSmsModel.getDestination());
        if (schedullerSmsModel.getListPhoneAsString() != null) {
            schedullerSmsModel.setListPhoneAsString(schedullerSmsModel.getListPhoneAsString() + ";" + schedullerSmsModel.getDestination());
        } else {
            schedullerSmsModel.setListPhoneAsString(schedullerSmsModel.getDestination());
        }
        schedullerSmsModel.setDestination("");
//        List<String> data = schedullerSmsModel.getListPhone();
//        System.out.println(" list datanya" + data);
//        if (data != null) {
//            String jsnon = jsonGsonConverter.getJson(data);
//            System.out.println(" Nilai Jnosn nya " + jsnon);
//        }

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
        definition.setDestination(jsonGsonConverter.getJson(model.getListPhone()));// for multi destination
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

    public void doSearch() {
        lazyDataModel = null;
    }

//    public void doEdit() {
//        try {
//            selectedTaskDefinition = taskDefinitionService.getEntiyByPK(selectedTaskDefinition.getId());
//            schedullerSmsModel = getModelFromEntity(selectedTaskDefinition);
//        } catch (Exception ex) {
//            LOGGER.error(ex, ex);
//        }
//    }
//    public TaskDefinition getSelectedTaskDefinition() {
//        return selectedTaskDefinition;
//    }
//
//    public void setSelectedTaskDefinition(TaskDefinition selectedTaskDefinition) {
//        this.selectedTaskDefinition = selectedTaskDefinition;
//    }
    public SchedullerSmsModel getModelFromEntity(TaskDefinition definition) {
        List<String> dataTosave = new ArrayList();
        schedullerSmsModel = new SchedullerSmsModel();
        schedullerSmsModel.setId(definition.getId());
        schedullerSmsModel.setDestination(definition.getDestination());
        schedullerSmsModel.setFromSending(definition.getFromSending());
        schedullerSmsModel.setIsRepeatOnCondition(definition.getIsRepeatOnCondition());
        schedullerSmsModel.setListPhoneAsString(definition.getDestination());
        schedullerSmsModel.setModemId(definition.getModemDefinition().getId());
        schedullerSmsModel.setName(definition.getName());
        schedullerSmsModel.setRepeatTime(definition.getRepeatTime());
        schedullerSmsModel.setScheduleType(definition.getScheduleType());
        schedullerSmsModel.setSendDate(definition.getDate());
        schedullerSmsModel.setSendTime(definition.getTime());
        schedullerSmsModel.setSmsContent(definition.getSmsContent());
        String dataList = definition.getDestination();
        StringTokenizer st = new StringTokenizer(dataList, ";");
        while (st.hasMoreTokens()) {
            dataTosave.add(st.nextToken());
        }
        schedullerSmsModel.setListPhone(dataTosave);
        return schedullerSmsModel;
    }

    public void onDelete() {

    }

//    public void doDelete() {
//        try {
//            this.taskDefinitionService.delete(selectedTaskDefinition);
//            lazyDataModel = null;
//        } catch (Exception ex) {
//            LOGGER.error(ex, ex);
//        }
//    }
    public void setJsonGsonConverter(JsonConverter jsonGsonConverter) {
        this.jsonGsonConverter = jsonGsonConverter;
    }

    public void setModemDefinitionService(ModemDefinitionService modemDefinitionService) {
        this.modemDefinitionService = modemDefinitionService;
    }

    public Map<String, Long> getMapOfModem() {
        return mapOfModem;
    }

    public void setMapOfModem(Map<String, Long> mapOfModem) {
        this.mapOfModem = mapOfModem;
    }

    public List<ModemDefinition> getListModemDefinitions() {
        return listModemDefinitions;
    }

    public void setListModemDefinitions(List<ModemDefinition> listModemDefinitions) {
        this.listModemDefinitions = listModemDefinitions;
    }

    public Boolean getIsEdit() {
        return isEdit;
    }

    public void setIsEdit(Boolean isEdit) {
        this.isEdit = isEdit;
    }

}
