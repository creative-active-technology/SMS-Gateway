/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inkubator.sms.gateway.web;

import com.inkubator.common.util.RandomNumberUtil;
import com.inkubator.sms.gateway.SMSGATEWAY;
import com.inkubator.sms.gateway.entity.Role;
import com.inkubator.sms.gateway.entity.SmsGatewayUser;
import com.inkubator.sms.gateway.entity.UserRole;
import com.inkubator.sms.gateway.entity.UserRoleId;
import com.inkubator.sms.gateway.service.RoleService;
import com.inkubator.sms.gateway.service.UserService;
import com.inkubator.sms.gateway.web.model.UserModel;
import com.inkubator.webcore.controller.BaseController;
import com.inkubator.webcore.util.FacesUtil;
import com.inkubator.webcore.util.MessagesResourceUtil;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.DualListModel;

/**
 *
 * @author deni
 */
@ManagedBean(name = "userFormController")
@ViewScoped
public class UserFormController extends BaseController {

    @ManagedProperty(value = "#{userService}")
    private UserService userService;
    @ManagedProperty(value = "#{roleService}")
    private RoleService roleService;
    private UserModel userModel;
    private Boolean isUpdate;
    private DualListModel<Role> dualListModel = new DualListModel<>();

    @PostConstruct
    @Override
    public void initialization() {
        try {
            super.initialization();
            String userId = FacesUtil.getRequestParameter("execution");
            if (userId != null) {
                isUpdate = Boolean.TRUE;
                SmsGatewayUser selectedUser = userService.getEntityByPkWithDetail(Long.parseLong(userId.substring(1)));
                userModel = getModelFromEntity(selectedUser);
                List<Role> sourceSpiRole = this.roleService.getAllData();
                List<Role> targetRole = selectedUser.getRoles();
                sourceSpiRole.removeAll(targetRole);
                dualListModel = new DualListModel<>(sourceSpiRole, targetRole);
            } else {
                List<Role> sourceSpiRole = this.roleService.getAllData();
                dualListModel.setSource(sourceSpiRole);
                userModel = new UserModel();
                isUpdate = Boolean.FALSE;
            }
//            passwordComplexity = passwordComplexityService.getByCode(HRMConstant.PASSWORD_CONFIG_CODE);
        } catch (Exception ex) {
            LOGGER.error("Error", ex);
        }
    }

    @PreDestroy
    public void cleanAndExit() {
        userModel = null;
        isUpdate = null;
        userService = null;
        dualListModel = null;
        roleService = null;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public Boolean getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(Boolean isUpdate) {
        this.isUpdate = isUpdate;
    }

    public DualListModel<Role> getDualListModel() {
        return dualListModel;
    }

    public void setDualListModel(DualListModel<Role> dualListModel) {
        this.dualListModel = dualListModel;
    }

    public RoleService getRoleService() {
        return roleService;
    }

    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    public String doSave() {
        SmsGatewayUser user = getEntityFromView(userModel);
        if (isUpdate) {
            return doUpdate(user);
        } else {
            return doInsert(user);
        }
    }

    private String doUpdate(SmsGatewayUser user) {
        try {
            SmsGatewayUser userExixting = userService.getEntiyByPK(user.getId());
//            boolean isDuplicateUserId = (hrmUserService.getByUserId(hrmUser.getUserId()) != null && !StringUtils.equals(hrmUserExixting.getUserId(), hrmUser.getUserId()));
//            boolean isDuplicateEmailAddress = (hrmUserService.getByEmailAddress(hrmUser.getEmailAddress()) != null && !StringUtils.equals(hrmUserExixting.getEmailAddress(), hrmUser.getEmailAddress()));
//            if (isDuplicateUserId) {
//                MessagesResourceUtil.setMessages(FacesMessage.SEVERITY_ERROR, "global.error", "user_form.duplicate_user_name",
//                        FacesUtil.getSessionAttribute(HRMConstant.BAHASA_ACTIVE).toString());
//                return null;
//            } else if (isDuplicateEmailAddress) {
//                MessagesResourceUtil.setMessages(FacesMessage.SEVERITY_ERROR, "global.error", "user_form.duplicate_email_address",
//                        FacesUtil.getSessionAttribute(HRMConstant.BAHASA_ACTIVE).toString());
//                return null;
//            } else {
            Set<UserRole> dataToSave = new HashSet<>();
            List<Role> roles = dualListModel.getTarget();
            for (Role role : roles) {
                UserRole userRole = new UserRole();
                userRole.setId(new UserRoleId(user.getId(), role.getId()));
                userRole.setRole(role);
                userRole.setUser(user);
                dataToSave.add(userRole);
            }
            user.setUserRoles(dataToSave);
            userService.update(user);
            MessagesResourceUtil.setMessagesFlas(FacesMessage.SEVERITY_INFO, "global.save_info", "global.update_successfully",
                    FacesUtil.getSessionAttribute(SMSGATEWAY.BAHASA_ACTIVE).toString());
            return "/protected/account/user_view.htm?faces-redirect=true";
        } catch (Exception ex) {
            LOGGER.error("Error", ex);
        }

        return null;
    }

    private String doInsert(SmsGatewayUser user) {
        user.setId(Long.parseLong(RandomNumberUtil.getRandomNumber(12)));
        try {
//            boolean isDuplicateUserId = hrmUserService.getByUserId(hrmUser.getUserId()) != null;
//            boolean isDuplicateEmailAddress = hrmUserService.getByEmailAddress(hrmUser.getEmailAddress()) != null;
//            if (isDuplicateUserId) {
//                MessagesResourceUtil.setMessages(FacesMessage.SEVERITY_ERROR, "global.error", "user_form.duplicate_user_name",
//                        FacesUtil.getSessionAttribute(HRMConstant.BAHASA_ACTIVE).toString());
//                return null;
//            } else if (isDuplicateEmailAddress) {
//                MessagesResourceUtil.setMessages(FacesMessage.SEVERITY_ERROR, "global.error", "user_form.duplicate_email_address",
//                        FacesUtil.getSessionAttribute(HRMConstant.BAHASA_ACTIVE).toString());
//                return null;
//            } else {
            Set<UserRole> dataToSave = new HashSet<>();
            List<Role> roles = dualListModel.getTarget();
            for (Role role : roles) {
                UserRole userRole = new UserRole();
                userRole.setId(new UserRoleId(user.getId(), role.getId()));
                userRole.setRole(role);
                userRole.setUser(user);
                dataToSave.add(userRole);
            }
            user.setUserRoles(dataToSave);
            userService.saveAndNotification(user);
            MessagesResourceUtil.setMessagesFlas(FacesMessage.SEVERITY_INFO, "global.save_info", "global.added_successfully",
                    FacesUtil.getSessionAttribute(SMSGATEWAY.BAHASA_ACTIVE).toString());
//                return "/protected/account/user_detail.htm?faces-redirect=true&execution=e" + user.getId();
            return "/protected/account/user_view.htm?faces-redirect=true";

//            }
        } catch (Exception ex) {
            LOGGER.error("Error", ex);
        }
        return null;
    }

    public SmsGatewayUser getEntityFromView(UserModel userModel) {
        SmsGatewayUser user = new SmsGatewayUser();
        if (userModel.getId() != null) {
            user.setId(userModel.getId());
        }
        user.setEmailAddress(userModel.getEmailAddress());
        if (userModel.getIsActive()) {
            user.setIsActive(1);
        } else {
            user.setIsActive(0);
        }

        if (userModel.getIsExpired()) {
            user.setIsExpired(1);
        } else {
            user.setIsExpired(0);
        }

        if (userModel.getIsLock()) {
            user.setIsLock(1);
        } else {
            user.setIsLock(0);
        }
        user.setPassword(userModel.getPassword());
        user.setPhoneNumber(userModel.getPhoneNumber());
        user.setRealName(userModel.getRealName());
        user.setUserId(userModel.getUserId());

        return user;
    }

    private UserModel getModelFromEntity(SmsGatewayUser entity) {
        UserModel userModel = new UserModel();
        userModel.setId(entity.getId());
        userModel.setRealName(entity.getRealName());
        userModel.setEmailAddress(entity.getEmailAddress());
        userModel.setUserId(entity.getUserId());
        userModel.setPhoneNumber(entity.getPhoneNumber());
        if (Objects.equals(entity.getIsActive(), SMSGATEWAY.ACTIVE)) {
            userModel.setIsActive(Boolean.TRUE);
        } else {
            userModel.setIsActive(Boolean.FALSE);
        }
        if (Objects.equals(entity.getIsExpired(), SMSGATEWAY.EXPIRED)) {
            userModel.setIsExpired(Boolean.TRUE);
        } else {
            userModel.setIsExpired(Boolean.FALSE);
        }
        if (Objects.equals(entity.getIsLock(), SMSGATEWAY.LOCK)) {
            userModel.setIsLock(Boolean.TRUE);
        } else {
            userModel.setIsLock(Boolean.FALSE);
        }
        return userModel;
    }
}
