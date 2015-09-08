package com.connehealth.entities;

import java.util.Date;
import java.util.List;

/**
 * Created by zhuyu on 2015/8/27.
 */
public class PatientSocialHistory extends AuditableEntity {
    private Long patientId;
    private String maritalStatus;
    private String smokingStatus;
    private String coffeeStatus;
    private String hivStatus;
    private String drinkStatus;
    private String drugStatus;
    private String sportStatus;
    private String occupation;
    private String religion;
    private String hobby;
    private String memo;


    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getSmokingStatus() {
        return smokingStatus;
    }

    public void setSmokingStatus(String smokingStatus) {
        this.smokingStatus = smokingStatus;
    }

    public String getCoffeeStatus() {
        return coffeeStatus;
    }

    public void setCoffeeStatus(String coffeeStatus) {
        this.coffeeStatus = coffeeStatus;
    }

    public String getHivStatus() {
        return hivStatus;
    }

    public void setHivStatus(String hivStatus) {
        this.hivStatus = hivStatus;
    }

    public String getDrinkStatus() {
        return drinkStatus;
    }

    public void setDrinkStatus(String drinkStatus) {
        this.drinkStatus = drinkStatus;
    }

    public String getDrugStatus() {
        return drugStatus;
    }

    public void setDrugStatus(String drugStatus) {
        this.drugStatus = drugStatus;
    }

    public String getSportStatus() {
        return sportStatus;
    }

    public void setSportStatus(String sportStatus) {
        this.sportStatus = sportStatus;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }
}
