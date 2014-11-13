/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inkubator.sms.gateway.web.model;

import java.io.Serializable;

/**
 *
 * @author Deni Husni FR
 */
public class ModemDefinitionModel implements Serializable {

    private Long id;
    private String modemId;
    private String model;
    private String manufacetur;
    private String comId;
    private Integer pinNumber;
    private String smscNumber;
    private Double pricePerSms;
    private Double currentValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModemId() {
        return modemId;
    }

    public void setModemId(String modemId) {
        this.modemId = modemId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacetur() {
        return manufacetur;
    }

    public void setManufacetur(String manufacetur) {
        this.manufacetur = manufacetur;
    }

    public String getComId() {
        return comId;
    }

    public void setComId(String comId) {
        this.comId = comId;
    }

    public Integer getPinNumber() {
        return pinNumber;
    }

    public void setPinNumber(Integer pinNumber) {
        this.pinNumber = pinNumber;
    }

    public String getSmscNumber() {
        return smscNumber;
    }

    public void setSmscNumber(String smscNumber) {
        this.smscNumber = smscNumber;
    }

    public Double getPricePerSms() {
        return pricePerSms;
    }

    public void setPricePerSms(Double pricePerSms) {
        this.pricePerSms = pricePerSms;
    }

    public Double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(Double currentValue) {
        this.currentValue = currentValue;
    }
    

}
