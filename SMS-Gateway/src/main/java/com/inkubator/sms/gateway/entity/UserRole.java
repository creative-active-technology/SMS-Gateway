/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inkubator.sms.gateway.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

/**
 *
 * @author deni
 */
@Entity
@Indexed
@Table(name = "user_role", catalog = "sms_gateway"
)
public class UserRole implements java.io.Serializable {

    private UserRoleId id;
    private Role role;
    private SmsGatewayUser user;
    private String description;

    public UserRole() {
    }

    public UserRole(UserRoleId id, Role role, SmsGatewayUser user) {
        this.id = id;
        this.role = role;
        this.user = user;
    }

    public UserRole(UserRoleId id, Role role, SmsGatewayUser user, String description) {
        this.id = id;
        this.role = role;
        this.user = user;
        this.description = description;
    }

    @EmbeddedId
    @DocumentId
    @FieldBridge(impl=UserFileBrigde.class) 
    @AttributeOverrides({
        @AttributeOverride(name = "userId", column = @Column(name = "user_id", nullable = false)),
        @AttributeOverride(name = "roleId", column = @Column(name = "role_id", nullable = false))})
    public UserRoleId getId() {
        return this.id;
    }

    public void setId(UserRoleId id) {
        this.id = id;
    }

    @IndexedEmbedded
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false, insertable = false, updatable = false)
    public Role getRole() {
        return this.role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @IndexedEmbedded
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
    public SmsGatewayUser getUser() {
        return this.user;
    }

    public void setUser(SmsGatewayUser user) {
        this.user = user;
    }

    @Column(name = "description", length = 65535, columnDefinition = "Text")
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
