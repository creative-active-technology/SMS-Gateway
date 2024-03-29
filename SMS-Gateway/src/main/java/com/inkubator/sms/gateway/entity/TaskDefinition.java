package com.inkubator.sms.gateway.entity;
// Generated Nov 13, 2014 9:27:59 AM by Hibernate Tools 4.3.1

import java.util.Date;
import java.util.Objects;
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
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;

/**
 * TaskDefinition generated by hbm2java
 */
@Entity
@Indexed
@Table(name = "task_definition", catalog = "sms_gateway", uniqueConstraints = @UniqueConstraint(columnNames = "name")
)
public class TaskDefinition implements java.io.Serializable {

    private long id;
    private Integer version;
    private ModemDefinition modemDefinition;
    private String name;
    private String scheduleType;// continu repeate
    private Date date;
    private Date time;
    private String destination;
    private String fromSending;
    private String smsContent;
    private String isRepeatOnCondition;// 
    private Integer repeatTime;
    private String createdBy;
    private Date cretedOn;
    private String updatedBy;
    private Date updatedOn;
    private Integer sendingCount;

    public TaskDefinition() {
    }

    public TaskDefinition(long id) {
        this.id = id;
    }

    public TaskDefinition(long id, ModemDefinition modemDefinition, String name, String scheduleType, Date date, Date time, String destination, String fromSending, String smsContent, String isRepeatOnCondition, Integer repeatTime, String createdBy, Date cretedOn, String updatedBy, Date updatedOn) {
        this.id = id;
        this.modemDefinition = modemDefinition;
        this.name = name;
        this.scheduleType = scheduleType;
        this.date = date;
        this.time = time;
        this.destination = destination;
        this.fromSending = fromSending;
        this.smsContent = smsContent;
        this.isRepeatOnCondition = isRepeatOnCondition;
        this.repeatTime = repeatTime;
        this.createdBy = createdBy;
        this.cretedOn = cretedOn;
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
    @JoinColumn(name = "modem_def_id")
    @IndexedEmbedded
    public ModemDefinition getModemDefinition() {
        return this.modemDefinition;
    }

    public void setModemDefinition(ModemDefinition modemDefinition) {
        this.modemDefinition = modemDefinition;
    }

    @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO)
    @Column(name = "name", unique = true, length = 45)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO)
    @Column(name = "schedule_type", length = 45)
    public String getScheduleType() {
        return this.scheduleType;
    }

    public void setScheduleType(String scheduleType) {
        this.scheduleType = scheduleType;
    }

    @Temporal(TemporalType.DATE)
    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    @Column(name = "date", length = 10)
    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Temporal(TemporalType.TIME)
    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    @Column(name = "time", length = 8)
    public Date getTime() {
        return this.time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    @Column(name = "destination", length = 45)
    public String getDestination() {
        return this.destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    @Column(name = "from_sending", length = 45)
    public String getFromSending() {
        return this.fromSending;
    }

    public void setFromSending(String fromSending) {
        this.fromSending = fromSending;
    }

    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    @Column(name = "sms_content", length = 65535)
    public String getSmsContent() {
        return this.smsContent;
    }

    public void setSmsContent(String smsContent) {
        this.smsContent = smsContent;
    }

    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    @Column(name = "repeat_Type")
    public String getIsRepeatOnCondition() {
        return this.isRepeatOnCondition;
    }

    public void setIsRepeatOnCondition(String isRepeatOnCondition) {
        this.isRepeatOnCondition = isRepeatOnCondition;
    }

    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    @Column(name = "repeat_time")
    public Integer getRepeatTime() {
        return this.repeatTime;
    }

    public void setRepeatTime(Integer repeatTime) {
        this.repeatTime = repeatTime;
    }

    @Column(name = "created_by", length = 45)
    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creted_on", length = 19)
    public Date getCretedOn() {
        return this.cretedOn;
    }

    public void setCretedOn(Date cretedOn) {
        this.cretedOn = cretedOn;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TaskDefinition other = (TaskDefinition) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    @Column(name = "sending_count")
    public Integer getSendingCount() {
        return sendingCount;
    }

    public void setSendingCount(Integer sendingCount) {
        this.sendingCount = sendingCount;
    }

}
