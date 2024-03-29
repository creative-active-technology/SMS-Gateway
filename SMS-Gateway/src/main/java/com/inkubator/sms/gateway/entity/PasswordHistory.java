/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inkubator.sms.gateway.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import org.hibernate.search.annotations.Indexed;

/**
 *
 * @author deni
 */
@Indexed
@Entity
@Table(name = "password_history", catalog = "sms_gateway"
)
public class PasswordHistory implements java.io.Serializable {

    private long id;
    private String password;
    private String createdBy;
    private Date createdOn;
    private String userName;
    private Integer emailNotification;
    private Integer smsNotification;
    private String requestType;
    private String realName;
    private String emailAddress;
    private String phoneNumber;
    private String listRole;
    private String localId;

    public PasswordHistory() {
    }

    public PasswordHistory(long id) {
        this.id = id;
    }

    public PasswordHistory(long id, String password, String createdBy, Date createdOn, String userName, Integer emailNotification, Integer smsNotification, String requestType, String realName, String emailAddress, String phoneNumber, String listRole) {
        this.id = id;
        this.password = password;
        this.createdBy = createdBy;
        this.createdOn = createdOn;
        this.userName = userName;
        this.emailNotification = emailNotification;
        this.smsNotification = smsNotification;
        this.requestType = requestType;
        this.realName = realName;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.listRole = listRole;
    }

    @Id

    @Column(name = "id", unique = true, nullable = false)
    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "password", length = 65535, columnDefinition="Text")
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "created_by", length = 45)
    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_on", length = 19)
    public Date getCreatedOn() {
        return this.createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    @Column(name = "user_name", length = 45)
    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    @Column(name = "email_notification")
    public Integer getEmailNotification() {
        return this.emailNotification;
    }

    public void setEmailNotification(Integer emailNotification) {
        this.emailNotification = emailNotification;
    }

    @Column(name = "sms_notification")
    public Integer getSmsNotification() {
        return this.smsNotification;
    }

    public void setSmsNotification(Integer smsNotification) {
        this.smsNotification = smsNotification;
    }

    @Column(name = "request_type", length = 45)
    public String getRequestType() {
        return this.requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    @Column(name = "real_name")
    public String getRealName() {
        return this.realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    @Column(name = "email_address")
    public String getEmailAddress() {
        return this.emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Column(name = "phone_number")
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Column(name = "list_role")
    public String getListRole() {
        return this.listRole;
    }

    public void setListRole(String listRole) {
        this.listRole = listRole;
    }

    @Column(name = "local_id")
    public String getLocalId() {
        return localId;
    }

    public void setLocalId(String localId) {
        this.localId = localId;
    }

    @Override
    public String toString() {
        return "PasswordHistory{" + "id=" + id + ", password=" + password + ", createdBy=" + createdBy + ", createdOn=" + createdOn + ", userName=" + userName + ", emailNotification=" + emailNotification + ", smsNotification=" + smsNotification + ", requestType=" + requestType + ", realName=" + realName + ", emailAddress=" + emailAddress + ", phoneNumber=" + phoneNumber + ", listRole=" + listRole + ", localId=" + localId + '}';
    }

    @Transient
    public String getEmailNotificationAsString() {
        String data = null;
        if(emailNotification == -1){
            data = "Email tidak perlu dikirim";
        }else if(emailNotification == 0){
            data = "Email belum dikirim";
        }else{
            data = "Email sudah dikirim";
        }
        
        return data;
    }
}
