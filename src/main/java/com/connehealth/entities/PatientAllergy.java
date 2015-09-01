package com.connehealth.entities;

import java.util.Date;

/**
 * Created by zhuyu on 2015/8/27.
 */
public class PatientAllergy extends AuditableEntity {
    private Long patientId;
    private Long allergenId;
    private String allergenType;
    private String allergenName;
    private String severity;
    private String stage;
    private String currentStatus;
    private Date startDate;
    private String memo;


    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getAllergenId() {
        return allergenId;
    }

    public void setAllergenId(Long allergenId) {
        this.allergenId = allergenId;
    }

    public String getAllergenType() {
        return allergenType;
    }

    public void setAllergenType(String allergenType) {
        this.allergenType = allergenType;
    }

    public String getAllergenName() {
        return allergenName;
    }

    public void setAllergenName(String allergenName) {
        this.allergenName = allergenName;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
