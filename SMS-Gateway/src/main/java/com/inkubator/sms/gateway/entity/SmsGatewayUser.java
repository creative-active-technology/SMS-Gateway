/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inkubator.sms.gateway.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

/**
 *
 * @author deni
 */
@Entity
@Indexed
@Table(name = "user", catalog = "sms_gateway", uniqueConstraints = {
    @UniqueConstraint(columnNames = "user_id"),
    @UniqueConstraint(columnNames = "email_address")}
)
public class SmsGatewayUser implements java.io.Serializable {

    private long id;
    private Integer version;
    private String userId;
    private String realName;
    private String emailAddress;
    private String phoneNumber;
    private String password;
    private Integer isActive;
    private Integer isLock;
    private Integer isExpired;
    private String createdBy;
    private Date createdOn;
    private String updatedBy;
    private Date updatedOn;
    private Set<UserRole> userRoles = new HashSet<UserRole>(0);
    private List<Role> roles = new ArrayList<>(0);

    public SmsGatewayUser() {
    }

    public SmsGatewayUser(long id) {
        this.id = id;
    }

    public SmsGatewayUser(long id, String userId, String realName, String emailAddress, String phoneNumber, String password, Integer isActive, Integer isLock, Integer isExpired, String createdBy, Date createdOn, String updatedBy, Date updatedOn, Set<UserRole> userRoles) {
        this.id = id;
        this.userId = userId;
        this.realName = realName;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.isActive = isActive;
        this.isLock = isLock;
        this.isExpired = isExpired;
        this.createdBy = createdBy;
        this.createdOn = createdOn;
        this.updatedBy = updatedBy;
        this.updatedOn = updatedOn;
        this.userRoles = userRoles;
    }

    @Id
    @DocumentId
    @Column(name = "id", unique = true, nullable = false)
    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Version
    @Column(name = "version")
    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    @Column(name = "user_id", unique = true, length = 45)
    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    @Column(name = "real_name", length = 100)
    public String getRealName() {
        return this.realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    @Column(name = "email_address", unique = true, length = 100)
    public String getEmailAddress() {
        return this.emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    @Column(name = "phone_number", length = 45)
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Column(name = "password", length = 65535, columnDefinition = "Text")
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "is_active")
    public Integer getIsActive() {
        return this.isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    @Column(name = "is_lock")
    public Integer getIsLock() {
        return this.isLock;
    }

    public void setIsLock(Integer isLock) {
        this.isLock = isLock;
    }

    @Column(name = "is_expired")
    public Integer getIsExpired() {
        return this.isExpired;
    }

    public void setIsExpired(Integer isExpired) {
        this.isExpired = isExpired;
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

    @Column(name = "updated_by", length = 45)
    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "updated_on", length = 10)
    public Date getUpdatedOn() {
        return this.updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    @ContainedIn
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    public Set<UserRole> getUserRoles() {
        return this.userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    @Transient
    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Transient
    public String getAcitveAsString() {
        String data = null;
        if (isActive == 1) {
            data = "Yes";
        }

        if (isActive == 0) {
            data = "No";
        }
        return data;
    }

    @Transient
    public String getLockAsString() {
        String data = null;
        if (isLock == 1) {
            data = "Yes";
        }

        if (isLock == 0) {
            data = "No";
        }
        return data;
    }

    @Transient
    public String getExpiredAsString() {
        String data = null;
        if (isExpired == 1) {
            data = "Yes";
        }

        if (isExpired == 0) {
            data = "No";
        }
        return data;
    }
}
