package com.connehealth.entities;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
public class Provider extends AuditableEntity {
    private Date applyOn;
    private Date approveOn;
    private Long approveBy;
    private boolean isApproved;

    private String certificateNo;
    private String practiceNo;
    private String practiceLocation;
    private String primaryPracticeName;
    private String professionalRank;
    private String specialties;
    private String skills;

    private UserProfile userProfile;
    public UserProfile getUserProfile() {
        return userProfile;
    }

    public Date getApplyOn() {
        return applyOn;
    }

    public void setApplyOn(Date applyOn) {
        this.applyOn = applyOn;
    }

    public Date getApproveOn() {
        return approveOn;
    }

    public void setApproveOn(Date approveOn) {
        this.approveOn = approveOn;
    }

    public Long getApproveBy() {
        return approveBy;
    }

    public void setApproveBy(Long approveBy) {
        this.approveBy = approveBy;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean isApproved) {
        this.isApproved = isApproved;
    }

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    public String getPracticeNo() {
        return practiceNo;
    }

    public void setPracticeNo(String practiceNo) {
        this.practiceNo = practiceNo;
    }

    public String getPracticeLocation() {
        return practiceLocation;
    }

    public void setPracticeLocation(String practiceLocation) {
        this.practiceLocation = practiceLocation;
    }

    public String getPrimaryPracticeName() {
        return primaryPracticeName;
    }

    public void setPrimaryPracticeName(String primaryPracticeName) {
        this.primaryPracticeName = primaryPracticeName;
    }

    public String getProfessionalRank() {
        return professionalRank;
    }

    public void setProfessionalRank(String professionalRank) {
        this.professionalRank = professionalRank;
    }

    public String getSpecialties() {
        return specialties;
    }

    public void setSpecialties(String specialties) {
        this.specialties = specialties;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }
}
