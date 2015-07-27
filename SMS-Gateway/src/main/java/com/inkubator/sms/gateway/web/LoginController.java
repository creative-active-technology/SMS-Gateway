/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inkubator.sms.gateway.web;

import com.inkubator.common.util.DateFormatter;
import com.inkubator.common.util.RandomNumberUtil;
import com.inkubator.sms.gateway.SMSGATEWAY;
import com.inkubator.sms.gateway.entity.SmsGatewayUser;
import com.inkubator.sms.gateway.service.IndexingLucenService;
import com.inkubator.sms.gateway.service.UserService;
import com.inkubator.webcore.controller.BaseController;
import com.inkubator.webcore.util.FacesUtil;
import com.inkubator.webcore.util.MessagesResourceUtil;
import java.io.IOException;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.primefaces.context.RequestContext;
import org.springframework.security.core.userdetails.User;

/**
 *
 * @author Deni Husni FR
 */
@ManagedBean(name = "loginController")
@RequestScoped
public class LoginController extends BaseController {

    private String userId;
    private String password;
    private String emailAddress;
    private String selectedLanguage;
    @ManagedProperty(value = "#{dateFormatter}")
    private DateFormatter dateFormatter;
    @ManagedProperty(value = "#{userService}")
    private UserService userService;
//    @ManagedProperty(value = "#{indexingLucenService}")
//    private IndexingLucenService indexingLucenService;

//    public String doLogin() {
//        System.out.println(" hahahahah");
//        return "/protected/home.htm?faces-redirect=true";
//    }
    @PostConstruct
    @Override
    public void initialization() {
        selectedLanguage = "in";
        FacesUtil.setSessionAttribute(SMSGATEWAY.BAHASA_ACTIVE, selectedLanguage);
        FacesUtil.getFacesContext().getViewRoot().setLocale(new Locale(selectedLanguage));
        System.out.println(" Hehrherhehreh");
    }

    public void doResetPassword() {
        RequestContext context = FacesUtil.getRequestContext();
        Boolean emailIsExist = Boolean.FALSE;
        try {
            SmsGatewayUser smsGatewayUser = userService.getByEmailAddressInNotLock(emailAddress);
            if (smsGatewayUser == null) {
                MessagesResourceUtil.setMessages(FacesMessage.SEVERITY_INFO, "global.error", "error.email_not_registered",
                        FacesUtil.getSessionAttribute(SMSGATEWAY.BAHASA_ACTIVE).toString());
            } else {
                emailIsExist = Boolean.TRUE;
                smsGatewayUser.setPassword("Inkuba" + RandomNumberUtil.getRandomNumber(7));
                userService.resetPassword(smsGatewayUser);

            }

        } catch (Exception ex) {
            LOGGER.error("Error", ex);
        }
        context.addCallbackParam("emailIsExist", emailIsExist);
    }

    public String doLogin() {
        System.out.println(" do login");
        ExternalContext context = FacesUtil.getExternalContext();
        RequestDispatcher dispatcher = ((ServletRequest) context.getRequest())
                .getRequestDispatcher("/" + SMSGATEWAY.SPRING_SECURITY_CHECK);
        try {
            dispatcher.forward((ServletRequest) context.getRequest(), (ServletResponse) context.getResponse());
        } catch (ServletException | IOException ex) {
            LOGGER.error("Error", ex);
        }
        FacesUtil.setSessionAttribute(SMSGATEWAY.LOGIN_DATE, dateFormatter.getDateFullAsStringsWithActiveLocale(new Date(),
                new Locale(selectedLanguage)));

        FacesUtil.getFacesContext().responseComplete();
        return null;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDateFormatter(DateFormatter dateFormatter) {
        this.dateFormatter = dateFormatter;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

//    public void doIndex(){
//        try {
//            indexingLucenService.indexAll();
//        } catch (Exception ex) {
//            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    public void setIndexingLucenService(IndexingLucenService indexingLucenService) {
//        this.indexingLucenService = indexingLucenService;
//    }
    
    
}
