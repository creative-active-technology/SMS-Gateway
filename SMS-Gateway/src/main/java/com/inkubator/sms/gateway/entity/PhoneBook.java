package com.inkubator.sms.gateway.entity;
// Generated Aug 6, 2015 9:56:56 AM by Hibernate Tools 4.3.1

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

/**
 * PhoneBook generated by hbm2java
 */
@Entity
@Indexed
@Table(name = "phone_book", catalog = "sms_gateway", uniqueConstraints = {
    @UniqueConstraint(columnNames = "mobile_phone"),
    @UniqueConstraint(columnNames = "email_address")}
)
public class PhoneBook implements java.io.Serializable {

    private long id;
    private Integer version;
    private GroupPhone groupPhone;
    private String name;
    private String mobilePhone;
    private String homePhone;
    private String officePhone;
    private String emailAddress;
    private String createdBy;
    private Date createdOn;
    private String updatedBy;
    private Date updatedOn;

    public PhoneBook() {
    }

    public PhoneBook(long id) {
        this.id = id;
    }

    public PhoneBook(long id, GroupPhone groupPhone, String name, String mobilePhone, String homePhone, String officePhone, String emailAddress, String createdBy, Date createdOn, String updatedBy, Date updatedOn) {
        this.id = id;
        this.groupPhone = groupPhone;
        this.name = name;
        this.mobilePhone = mobilePhone;
        this.homePhone = homePhone;
        this.officePhone = officePhone;
        this.emailAddress = emailAddress;
        this.createdBy = createdBy;
        this.createdOn = createdOn;
        this.updatedBy = updatedBy;
        this.updatedOn = updatedOn;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    public GroupPhone getGroupPhone() {
        return this.groupPhone;
    }

    public void setGroupPhone(GroupPhone groupPhone) {
        this.groupPhone = groupPhone;
    }

    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    @Column(name = "name", length = 100)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    @Column(name = "mobile_phone", unique = true, length = 45)
    public String getMobilePhone() {
        return this.mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    @Column(name = "home_phone", length = 45)
    public String getHomePhone() {
        return this.homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    @Column(name = "office_phone", length = 45)
    public String getOfficePhone() {
        return this.officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    @Column(name = "email_address", unique = true, length = 45)
    public String getEmailAddress() {
        return this.emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_on", length = 19)
    public Date getUpdatedOn() {
        return this.updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

}
