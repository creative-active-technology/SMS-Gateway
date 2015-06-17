/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inkubator.sms.gateway.web;

import com.inkubator.common.notification.model.SerialGateWay;
import com.inkubator.sms.gateway.entity.ModemDefinition;
import com.inkubator.sms.gateway.service.ModemDefinitionService;
import com.inkubator.sms.gateway.service.impl.ModemManageService;
import com.inkubator.webcore.controller.BaseController;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.smslib.AGateway;
import org.smslib.GatewayException;
import org.smslib.SMSLibException;
import org.smslib.Service;
import org.smslib.TimeoutException;
import org.smslib.USSDRequest;

/**
 *
 * @author Deni Husni FR
 */
@ManagedBean(name = "modemViewController")
@ViewScoped
public class ModemViewController extends BaseController {

    @ManagedProperty(value = "#{modemDefinitionService}")
    private ModemDefinitionService modemDefinitionService;
    private List<ModemDefinition> dataModemDefinitions = new ArrayList<>();
    private String param;
    private ModemDefinition selectedModemDefinition;
    @ManagedProperty(value = "#{modemManageService}")
    private ModemManageService modemManageService;

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
    @PostConstruct
    public void initialization() {
        try {
            dataModemDefinitions = this.modemDefinitionService.getAllByFullText(param);
        } catch (Exception ex) {
            LOGGER.error(ex, ex);
        }
    }

    public String doRedirectModemConfig() {
        return "/protected/modem_config.htm?faces-redirect=true";
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String doAdd() {
        return "/protected/modem_config_form.htm?faces-redirect=true";
    }

    public ModemDefinition getSelectedModemDefinition() {
        return selectedModemDefinition;
    }

    public void setSelectedModemDefinition(ModemDefinition selectedModemDefinition) {
        this.selectedModemDefinition = selectedModemDefinition;
    }

    public String doEdit() {
        return "/protected/modem_config_form.htm?faces-redirect=true&execution=e" + selectedModemDefinition.getId();
    }

    public String doDeteail() {
        return "/protected/modem_config_detail.htm?faces-redirect=true&execution=e" + selectedModemDefinition.getId();
    }

    public void doDelete() {
        try {
            modemDefinitionService.delete(selectedModemDefinition);
            dataModemDefinitions = this.modemDefinitionService.getAllByFullText(param);
        } catch (Exception ex) {
            LOGGER.error(ex, ex);
        }
    }

    public void doSearch() {
        try {
            dataModemDefinitions = this.modemDefinitionService.getAllByFullText(param);
        } catch (Exception ex) {
            LOGGER.error(ex, ex);
        }
    }

    public void doConnect() {
        try {
            if (Service.getInstance().getServiceStatus().equals(Service.ServiceStatus.STOPPED)) {
                dataModemDefinitions = this.modemDefinitionService.getAllData();
                for (ModemDefinition dataModemDefinition : dataModemDefinitions) {
                    if (Service.getInstance().getGateway(dataModemDefinition.getModemId()) == null) {
                        SerialGateWay gateWay = new SerialGateWay();
                        gateWay.setBaudRate(dataModemDefinition.getBaudRate());
                        gateWay.setComPort(dataModemDefinition.getComId());
                        gateWay.setInBound(true);
                        gateWay.setManaufactur(dataModemDefinition.getManufacture());
                        gateWay.setModelName(dataModemDefinition.getModel());
                        gateWay.setModemId(dataModemDefinition.getModemId());
                        gateWay.setOutBound(true);
                        gateWay.setPinNumber(String.valueOf(dataModemDefinition.getPinNumber()));
                        gateWay.setSmscNumber(dataModemDefinition.getSmscNumber());

                        modemManageService.startServiceAndAddGateway(gateWay);
                    }
                }
                Service.getInstance().startService(false);
            }
        } catch (Exception ex) {
            LOGGER.error(ex, ex);
        }
    }

    public void setModemManageService(ModemManageService modemManageService) {
        this.modemManageService = modemManageService;
    }

    public void doOffService() {

        try {
            dataModemDefinitions = this.modemDefinitionService.getAllData();
            for (ModemDefinition dataModemDefinition : dataModemDefinitions) {
                Service.getInstance().getGateway(dataModemDefinition.getModemId()).stopGateway();
                Service.getInstance().stopService();
            }
        } catch (Exception ex) {
            Logger.getLogger(ModemViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void doCheckBalance() {
        USSDRequest request = new USSDRequest("AT+CUSD=1,\"*888#\",15\r");
        System.out.println(" Ini perintahnya USSD nya" + request.getContent());
        for (ModemDefinition dataModemDefinition : dataModemDefinitions) {
            try {
                AGateway aGateway = Service.getInstance().findGateway(dataModemDefinition.getModemId());
                aGateway.sendUSSDCommand("*888#");
                System.out.println(" ini berhasisisisillll");
//                 Service.getInstance().sendUSSDRequest(request, dataModemDefinition.getModemId());
            } catch (GatewayException | TimeoutException | IOException | InterruptedException ex) {
                Logger.getLogger(ModemViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}
