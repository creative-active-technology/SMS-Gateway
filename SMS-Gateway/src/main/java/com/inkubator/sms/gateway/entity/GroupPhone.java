package com.inkubator.sms.gateway.entity;
// Generated Aug 6, 2015 9:56:56 AM by Hibernate Tools 4.3.1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

/**
 * GroupPhone generated by hbm2java
 */
@Entity
@Table(name="group_phone"
    ,catalog="sms_gateway"
    , uniqueConstraints = @UniqueConstraint(columnNames="name") 
)
public class GroupPhone  implements java.io.Serializable {


     private long id;
     private Integer version;
     private String name;
     private String description;
     private String createdBy;
     private Date createdOn;
     private String updatedBy;
     private Date updatedOn;
     private Set<PhoneBook> phoneBooks = new HashSet<PhoneBook>(0);

    public GroupPhone() {
    }

	
    public GroupPhone(long id) {
        this.id = id;
    }
    public GroupPhone(long id, String name, String description, String createdBy, Date createdOn, String updatedBy, Date updatedOn, Set<PhoneBook> phoneBooks) {
       this.id = id;
       this.name = name;
       this.description = description;
       this.createdBy = createdBy;
       this.createdOn = createdOn;
       this.updatedBy = updatedBy;
       this.updatedOn = updatedOn;
       this.phoneBooks = phoneBooks;
    }
   
     @Id 

    
    @Column(name="id", unique=true, nullable=false)
    public long getId() {
        return this.id;
    }
    
    public void setId(long id) {
        this.id = id;
    }

    @Version
    @Column(name="version")
    public Integer getVersion() {
        return this.version;
    }
    
    public void setVersion(Integer version) {
        this.version = version;
    }

    
    @Column(name="name", unique=true, length=45)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    
    @Column(name="description", length=45)
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    
    @Column(name="created_by", length=45)
    public String getCreatedBy() {
        return this.createdBy;
    }
    
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_on", length=19)
    public Date getCreatedOn() {
        return this.createdOn;
    }
    
    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    
    @Column(name="updated_by", length=45)
    public String getUpdatedBy() {
        return this.updatedBy;
    }
    
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="updated_on", length=19)
    public Date getUpdatedOn() {
        return this.updatedOn;
    }
    
    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="groupPhone")
    public Set<PhoneBook> getPhoneBooks() {
        return this.phoneBooks;
    }
    
    public void setPhoneBooks(Set<PhoneBook> phoneBooks) {
        this.phoneBooks = phoneBooks;
    }




}

