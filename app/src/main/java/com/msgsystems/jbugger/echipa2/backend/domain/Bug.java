package com.msgsystems.jbugger.echipa2.backend.domain;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;

@Entity
@Table(name = "bugs")
public class Bug {
    @Id
    @Column(name="id_bug")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="title")
    private String title;

    @Column(name="description", length = 1000)
    private String description;

    @Column(name="version")
    private String version;

    @DateTimeFormat
    @Column(name="targetDate")
    private Date targetDate;

    @Column(name="severity")
    private String severity;

    @Column(name="status")
    private String status;

    @Column(name="fixedVersion")
    private String fixedVersion;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User createdBy;

    //TODO -> attachments

    public Bug(){
        this.status = "OPEN";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Date getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(Date targetDate) {
        this.targetDate = targetDate;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getFixedVersion() {
        return fixedVersion;
    }

    public void setFixedVersion(String fixedVersion) {
        this.fixedVersion = fixedVersion;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return this.getTitle();
    }
}
