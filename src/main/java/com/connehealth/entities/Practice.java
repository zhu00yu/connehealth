package com.connehealth.entities;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Created by zhuyu on 2015/8/7.
 */
@XmlRootElement
public class Practice extends AuditableEntity {
    private String type;
    private String name;
    private String practiceLicenseCode;
    private String issuer;
    private Date effectiveDate;
    private Date expiryDate;
    private String legalPerson;
    private String principal;
    private String email;
    private Integer provinceId;
    private Integer cityId;
    private Integer districtId;
    private String zip;
    private String address;
    private Long practiceLicenseFileId;
    private String businessPhone;
    private Integer numberOfDoctors;
    private Integer numberOfBeds;
    private boolean isPublicOrg;
    private String businessLicenseCode;
    private Integer registeredFund;
    private Long businessLicenseFileId;
    private Long organizationCodeFileId;
    private Long taxEnrolFileId;
    private boolean isChainPractice;
    private String chainPracticeName;
    private Date applyOn;
    private Date approveOn;
    private Long approveBy;
    private boolean isApproved;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPracticeLicenseCode() {
        return practiceLicenseCode;
    }

    public void setPracticeLicenseCode(String practiceLicenseCode) {
        this.practiceLicenseCode = practiceLicenseCode;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getPracticeLicenseFileId() {
        return practiceLicenseFileId;
    }

    public void setPracticeLicenseFileId(Long practiceLicenseFileId) {
        this.practiceLicenseFileId = practiceLicenseFileId;
    }

    public String getBusinessPhone() {
        return businessPhone;
    }

    public void setBusinessPhone(String businessPhone) {
        this.businessPhone = businessPhone;
    }

    public Integer getNumberOfDoctors() {
        return numberOfDoctors;
    }

    public void setNumberOfDoctors(Integer numberOfDoctors) {
        this.numberOfDoctors = numberOfDoctors;
    }

    public Integer getNumberOfBeds() {
        return numberOfBeds;
    }

    public void setNumberOfBeds(Integer numberOfBeds) {
        this.numberOfBeds = numberOfBeds;
    }

    public boolean isPublicOrg() {
        return isPublicOrg;
    }

    public void setPublicOrg(boolean isPublicOrg) {
        this.isPublicOrg = isPublicOrg;
    }

    public String getBusinessLicenseCode() {
        return businessLicenseCode;
    }

    public void setBusinessLicenseCode(String businessLicenseCode) {
        this.businessLicenseCode = businessLicenseCode;
    }

    public Integer getRegisteredFund() {
        return registeredFund;
    }

    public void setRegisteredFund(Integer registeredFund) {
        this.registeredFund = registeredFund;
    }

    public Long getBusinessLicenseFileId() {
        return businessLicenseFileId;
    }

    public void setBusinessLicenseFileId(Long businessLicenseFileId) {
        this.businessLicenseFileId = businessLicenseFileId;
    }

    public Long getOrganizationCodeFileId() {
        return organizationCodeFileId;
    }

    public void setOrganizationCodeFileId(Long organizationCodeFileId) {
        this.organizationCodeFileId = organizationCodeFileId;
    }

    public Long getTaxEnrolFileId() {
        return taxEnrolFileId;
    }

    public void setTaxEnrolFileId(Long taxEnrolFileId) {
        this.taxEnrolFileId = taxEnrolFileId;
    }

    public boolean isChainPractice() {
        return isChainPractice;
    }

    public void setChainPractice(boolean isChainPractice) {
        this.isChainPractice = isChainPractice;
    }

    public String getChainPracticeName() {
        return chainPracticeName;
    }

    public void setChainPracticeName(String chainPracticeName) {
        this.chainPracticeName = chainPracticeName;
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
}
