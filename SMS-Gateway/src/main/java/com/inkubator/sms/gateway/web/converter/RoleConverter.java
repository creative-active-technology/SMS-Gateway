/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inkubator.sms.gateway.web.converter;

import com.inkubator.sms.gateway.entity.Role;
import com.inkubator.sms.gateway.service.RoleService;
import com.inkubator.webcore.util.ServiceWebUtil;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.apache.log4j.Logger;

/**
 *
 * @author deni
 */
@FacesConverter(value = "roleConverter")
public class RoleConverter implements Converter {

    private static final Logger LOGGER = Logger.getLogger(RoleConverter.class);
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        RoleService hrmRoleService = (RoleService) ServiceWebUtil.getService("roleService");
        Object object = null;
        try {
            object = hrmRoleService.getEntiyByPK(Long.parseLong(value));
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
        return object;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return String.valueOf(((Role) value).getId());
    }

}
