package com.connehealth.entities;

import java.util.Date;

/**
 * Created by zhuyu on 2015/8/27.
 */
public class PatientProblem extends AuditableEntity {
    private Long patientId;
    private Long diseaseId;
    private String problemName;
    private String icdNo;
    private String currentStatus;
    private String severity;
    private Date startDate;
    private Date endDate;
    private String memo;

    public Long getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(Long diseaseId) {
        this.diseaseId = diseaseId;
    }

    public String getProblemName() {
        return problemName;
    }

    public void setProblemName(String problemName) {
        this.problemName = problemName;
    }

    public String getIcdNo() {
        return icdNo;
    }

    public void setIcdNo(String icdNo) {
        this.icdNo = icdNo;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }
}
