/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inkubator.sms.gateway.util;

import com.inkubator.common.util.DateFormatter;
import com.inkubator.securitycore.util.AuthenticationLogoutSuccessHandler;
import com.inkubator.sms.gateway.SMSGATEWAY;
import com.inkubator.sms.gateway.entity.LoginHistory;
import com.inkubator.sms.gateway.service.LoginHistoryService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

/**
 *
 * @author deni
 */
public class CustomAuthenticationLogoutSuccessHandler extends AuthenticationLogoutSuccessHandler{
    @Autowired
    private LoginHistoryService loginHistoryService;
    @Autowired
    private DateFormatter dateFormatter;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        try {
            if (request.getSession().getAttribute(SMSGATEWAY.USER_LOGIN_ID) != null) {
                Long id = (Long) request.getSession().getAttribute(SMSGATEWAY.USER_LOGIN_ID);
                LoginHistory loginHistory = new LoginHistory();
                loginHistory.setId(id);
                this.loginHistoryService.updateAndPushMessage(loginHistory);                
                LOGGER.info(authentication.getName() + " Success Logout");
            }
        } catch (Exception ex) {
            LOGGER.error("Error", ex);
        }
    }
}
