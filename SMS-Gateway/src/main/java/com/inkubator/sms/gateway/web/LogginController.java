/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inkubator.sms.gateway.web;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Deni Husni FR
 */
@ManagedBean(name = "logginController")
@RequestScoped
public class LogginController{
    
    
    public String doLogin (){
        System.out.println(" hahahahah");
         return "/protected/home.htm?faces-redirect=true";
    }
}
