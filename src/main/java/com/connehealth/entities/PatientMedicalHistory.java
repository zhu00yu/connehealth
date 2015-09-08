package com.connehealth.entities;

import java.util.Date;
import java.util.List;

/**
 * Created by zhuyu on 2015/8/27.
 */
public class PatientMedicalHistory extends AuditableEntity {
    private Long patientId;
    private String problem;
    private String therapyCode;
    private String therapy;
    private String goals;
    private String outcome;  //治愈、有效、无效
    private String practiceName;
    private String attendingDoctor;
    private String anesthetist;
    private Date therapyDate;
    private Date recordDate;
    private boolean isHospitalized;
    private String residency;
    private Date admissionDate;
    private Date dischargeDate;
    private Long dischargeSummaryFileId;
    private String memo;


    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getTherapy() {
        return therapy;
    }

    public void setTherapy(String therapy) {
        this.therapy = therapy;
    }

    public String getGoals() {
        return goals;
    }

    public void setGoals(String goals) {
        this.goals = goals;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public String getPracticeName() {
        return practiceName;
    }

    public void setPracticeName(String practiceName) {
        this.practiceName = practiceName;
    }

    public String getAttendingDoctor() {
        return attendingDoctor;
    }

    public void setAttendingDoctor(String attendingDoctor) {
        this.attendingDoctor = attendingDoctor;
    }

    public String getAnesthetist() {
        return anesthetist;
    }

    public void setAnesthetist(String anesthetist) {
        this.anesthetist = anesthetist;
    }

    public Date getTherapyDate() {
        return therapyDate;
    }

    public void setTherapyDate(Date therapyDate) {
        this.therapyDate = therapyDate;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public boolean isHospitalized() {
        return isHospitalized;
    }

    public void setHospitalized(boolean isHospitalized) {
        this.isHospitalized = isHospitalized;
    }

    public String getResidency() {
        return residency;
    }

    public void setResidency(String residency) {
        this.residency = residency;
    }

    public Date getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(Date admissionDate) {
        this.admissionDate = admissionDate;
    }

    public Date getDischargeDate() {
        return dischargeDate;
    }

    public void setDischargeDate(Date dischargeDate) {
        this.dischargeDate = dischargeDate;
    }

    public Long getDischargeSummaryFileId() {
        return dischargeSummaryFileId;
    }

    public void setDischargeSummaryFileId(Long dischargeSummaryFileId) {
        this.dischargeSummaryFileId = dischargeSummaryFileId;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getTherapyCode() {
        return therapyCode;
    }

    public void setTherapyCode(String therapyCode) {
        this.therapyCode = therapyCode;
    }
}
