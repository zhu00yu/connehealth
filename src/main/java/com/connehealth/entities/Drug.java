package com.connehealth.entities;

import java.util.Date;

/**
 * Created by zhuyu on 2015/8/27.
 */
public class Drug extends AuditableEntity {
    // 国药准字
    private String drugApproveCode;
    private Date approveDate;
    // 药品本位码
    private String drugStandardCode;

    private String type;
    private boolean isOtcDrug;
    private boolean isBasicDrug;
    private boolean isMedicareDrug;

    private String mnemonics1;
    private String mnemonics2;
    private String generalName;
    private String brandName;
    private String form;
    private String strength;
    private String strengthUnit;
    private String packaging;

    private String company;
    private String companyCountry;
    private String companyAddress;

    private String manufacturer;
    private String manufacturerCountry;
    private String manufacturerAddress;

    private Long drugPhotoId1;
    private Long drugPhotoId2;
    private Long drugPhotoId3;

    public String getDrugApproveCode() {
        return drugApproveCode;
    }

    public void setDrugApproveCode(String drugApproveCode) {
        this.drugApproveCode = drugApproveCode;
    }

    public Date getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(Date approveDate) {
        this.approveDate = approveDate;
    }

    public String getDrugStandardCode() {
        return drugStandardCode;
    }

    public void setDrugStandardCode(String drugStandardCode) {
        this.drugStandardCode = drugStandardCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isOtcDrug() {
        return isOtcDrug;
    }

    public void setOtcDrug(boolean isOtcDrug) {
        this.isOtcDrug = isOtcDrug;
    }

    public boolean isBasicDrug() {
        return isBasicDrug;
    }

    public void setBasicDrug(boolean isBasicDrug) {
        this.isBasicDrug = isBasicDrug;
    }

    public boolean isMedicareDrug() {
        return isMedicareDrug;
    }

    public void setMedicareDrug(boolean isMedicareDrug) {
        this.isMedicareDrug = isMedicareDrug;
    }

    public String getGeneralName() {
        return generalName;
    }

    public void setGeneralName(String generalName) {
        this.generalName = generalName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public String getPackaging() {
        return packaging;
    }

    public void setPackaging(String packaging) {
        this.packaging = packaging;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompanyCountry() {
        return companyCountry;
    }

    public void setCompanyCountry(String companyCountry) {
        this.companyCountry = companyCountry;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getManufacturerCountry() {
        return manufacturerCountry;
    }

    public void setManufacturerCountry(String manufacturerCountry) {
        this.manufacturerCountry = manufacturerCountry;
    }

    public String getManufacturerAddress() {
        return manufacturerAddress;
    }

    public void setManufacturerAddress(String manufacturerAddress) {
        this.manufacturerAddress = manufacturerAddress;
    }

    public Long getDrugPhotoId1() {
        return drugPhotoId1;
    }

    public void setDrugPhotoId1(Long drugPhotoId1) {
        this.drugPhotoId1 = drugPhotoId1;
    }

    public Long getDrugPhotoId2() {
        return drugPhotoId2;
    }

    public void setDrugPhotoId2(Long drugPhotoId2) {
        this.drugPhotoId2 = drugPhotoId2;
    }

    public Long getDrugPhotoId3() {
        return drugPhotoId3;
    }

    public void setDrugPhotoId3(Long drugPhotoId3) {
        this.drugPhotoId3 = drugPhotoId3;
    }

    public String getMnemonics1() {
        return mnemonics1;
    }

    public void setMnemonics1(String mnemonics1) {
        this.mnemonics1 = mnemonics1;
    }

    public String getMnemonics2() {
        return mnemonics2;
    }

    public void setMnemonics2(String mnemonics2) {
        this.mnemonics2 = mnemonics2;
    }

    public String getStrengthUnit() {
        return strengthUnit;
    }

    public void setStrengthUnit(String strengthUnit) {
        this.strengthUnit = strengthUnit;
    }
}
