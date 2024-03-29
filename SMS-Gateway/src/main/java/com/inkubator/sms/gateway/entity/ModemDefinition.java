package com.inkubator.sms.gateway.entity;
// Generated Nov 13, 2014 9:27:59 AM by Hibernate Tools 4.3.1

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
import org.smslib.Service;

/**
 * ModemDefinition generated by hbm2java
 */
@Entity
@Indexed
@Table(name = "modem_definition", catalog = "sms_gateway", uniqueConstraints = {
    @UniqueConstraint(columnNames = "com_id"),
    @UniqueConstraint(columnNames = "modem_id")}
)
public class ModemDefinition implements java.io.Serializable {

    private long id;
    private Integer version;
    private String model;
    private String manufacture;
    private String comId;
    private Integer pinNumber;
    private String smscNumber;
    private Double pricePerSms;
    private String createdBy;
    private Date createdOn;
    private String updatedBy;
    private Date updatedOn;
    private String modemId;
    private Double currentValue;
    private Integer baudRate;
    private Boolean isConnected;
    private String phoneNumber;
    private String checkPulsa;
    private Set<TaskDefinition> taskDefinitions = new HashSet<TaskDefinition>(0);

    public ModemDefinition() {
    }

    public ModemDefinition(long id) {
        this.id = id;
    }

    public ModemDefinition(long id, String model, String manufacture, String comId, Integer portNumber, String smscNumber, Double pricePerSms, String createdBy, Date createdOn, String updatedBy, Date updatedOn, String modemId, Double currentValue, Set<TaskDefinition> taskDefinitions) {
        this.id = id;
        this.model = model;
        this.manufacture = manufacture;
        this.comId = comId;
        this.pinNumber = portNumber;
        this.smscNumber = smscNumber;
        this.pricePerSms = pricePerSms;
        this.createdBy = createdBy;
        this.createdOn = createdOn;
        this.updatedBy = updatedBy;
        this.updatedOn = updatedOn;
        this.modemId = modemId;
        this.currentValue = currentValue;
        this.taskDefinitions = taskDefinitions;
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
    @Column(name = "model", length = 45)
    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.YES)
    @Column(name = "manufacture", length = 45)
    public String getManufacture() {
        return this.manufacture;
    }

    public void setManufacture(String manufacture) {
        this.manufacture = manufacture;
    }

    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    @Column(name = "com_id", unique = true, length = 45)
    public String getComId() {
        return this.comId;
    }

    public void setComId(String comId) {
        this.comId = comId;
    }

    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    @Column(name = "pin_number")
    public Integer getPinNumber() {
        return pinNumber;
    }

    public void setPinNumber(Integer pinNumber) {
        this.pinNumber = pinNumber;
    }

    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    @Column(name = "smsc_number", length = 45)
    public String getSmscNumber() {
        return this.smscNumber;
    }

    public void setSmscNumber(String smscNumber) {
        this.smscNumber = smscNumber;
    }

    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    @Column(name = "price_per_sms", precision = 22, scale = 0)
    public Double getPricePerSms() {
        return this.pricePerSms;
    }

    public void setPricePerSms(Double pricePerSms) {
        this.pricePerSms = pricePerSms;
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

    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    @Column(name = "modem_id", unique = true, length = 45)
    public String getModemId() {
        return this.modemId;
    }

    public void setModemId(String modemId) {
        this.modemId = modemId;
    }

    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    @Column(name = "current_value", precision = 22, scale = 0)
    public Double getCurrentValue() {
        return this.currentValue;
    }

    public void setCurrentValue(Double currentValue) {
        this.currentValue = currentValue;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "modemDefinition")
    @ContainedIn
    public Set<TaskDefinition> getTaskDefinitions() {
        return this.taskDefinitions;
    }

    public void setTaskDefinitions(Set<TaskDefinition> taskDefinitions) {
        this.taskDefinitions = taskDefinitions;
    }

    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    @Column(name = "baud_rate")
    public Integer getBaudRate() {
        return baudRate;
    }

    public void setBaudRate(Integer baudRate) {
        this.baudRate = baudRate;
    }

    @Transient
    public Boolean getIsConnected() {
        if (Service.getInstance().getServiceStatus().equals(Service.ServiceStatus.STARTED)) {
            return true;
        }
        if (Service.getInstance().getServiceStatus().equals(Service.ServiceStatus.STOPPED)) {
            return false;
        }
        return null;
    }

    @Column(name = "phone_number", length = 45)
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Column(name = "check_pulsa", length = 45)
    public String getCheckPulsa() {
        return checkPulsa;
    }

    public void setCheckPulsa(String checkPulsa) {
        this.checkPulsa = checkPulsa;
    }

}
