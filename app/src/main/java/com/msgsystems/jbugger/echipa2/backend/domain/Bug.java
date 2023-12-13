package com.msgsystems.jbugger.echipa2.backend.domain;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "bugs")
public class Bug {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bug")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false, length = 1000)
    private String description;

    @Column(name = "revision_version", nullable = false)
    private String revisionVersion;

    @Column(name = "fixed_in_version")
    private String fixedInVersion;

    @Temporal(TemporalType.DATE)
    @Column(name = "target_date")
    private Date targetDate;

    @Column(name = "severity", nullable = false)
    private BugSeverity severity;

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Column(name = "status", nullable = false)
    private BugStatus status;

    @Column(name = "assigned_to")
    private String assignedTo;

    @Lob
    @Column(name = "attachment")
    private byte[] attachment;

    // Constructors
    public Bug() {
        this.status=BugStatus.NEW;
    }

    public Bug(String title, String description, String revisionVersion,
               String fixedInVersion, Date targetDate, BugSeverity severity,
               String createdBy, String assignedTo,
               byte[] attachment) {
        this.title = title;
        this.description = description;
        this.revisionVersion = revisionVersion;
        this.fixedInVersion = fixedInVersion;
        this.targetDate = targetDate;
        this.severity = severity;
        this.createdBy = createdBy;
        this.status = BugStatus.NEW;
        this.assignedTo = assignedTo;
        this.attachment = attachment;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getRevisionVersion() {
        return revisionVersion;
    }

    public String getFixedInVersion() {
        return fixedInVersion;
    }

    public Date getTargetDate() {
        return targetDate;
    }

    public BugSeverity getSeverity() {
        return severity;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public BugStatus getStatus() {
        return status;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public byte[] getAttachment() {
        return attachment;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRevisionVersion(String revisionVersion) {
        this.revisionVersion = revisionVersion;
    }

    public void setFixedInVersion(String fixedInVersion) {
        this.fixedInVersion = fixedInVersion;
    }

    public void setTargetDate(Date targetDate) {
        this.targetDate = targetDate;
    }

    public void setSeverity(BugSeverity severity) {
        this.severity = severity;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setStatus(BugStatus status) {
        this.status = status;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public void setAttachment(byte[] attachment) {
        this.attachment = attachment;
    }

    @Override
    public String toString() {
        return "Bug{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", revisionVersion='" + revisionVersion + '\'' +
                ", fixedInVersion='" + fixedInVersion + '\'' +
                ", targetDate=" + targetDate +
                ", severity='" + severity + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", status='" + status + '\'' +
                ", assignedTo='" + assignedTo + '\'' +
                ", attachment=" + (attachment != null ? "present" : "null") +
                '}';
    }
}


