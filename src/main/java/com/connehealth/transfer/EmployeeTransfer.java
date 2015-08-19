package com.connehealth.transfer;

import com.connehealth.entities.Employee;
import com.connehealth.entities.PatientFile;
import com.connehealth.entities.Provider;
import com.connehealth.entities.UserProfile;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * Created by zhuyu on 2015/8/10.
 */
public class EmployeeTransfer {
    private Long id;
    private Long practiceId;
    private Long userId;
    private boolean isManager;

    private String familyName;
    private String givenName;
    private Date dob;
    private Long sex;
    private String email;
    private String mobile;
    private Long provinceId;
    private Long cityId;
    private Long districtId;
    private String zip;
    private String address;

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

    public EmployeeTransfer(Employee employee){
        Provider provider = employee.getProvider();
        UserProfile profile = provider.getUserProfile();

        this.id = employee.getId();
        this.practiceId = employee.getPracticeId();
        this.userId = employee.getUserId();
        this.isManager = employee.isManager();

        this.familyName = profile.getFamilyName();
        this.givenName = profile.getGivenName();
        this.dob = profile.getDob();
        this.sex = profile.getSex();
        this.email = profile.getEmail();
        this.mobile = profile.getMobile();
        this.provinceId = profile.getProvinceId();
        this.cityId = profile.getCityId();
        this.districtId = profile.getDistrictId();
        this.zip = profile.getZip();
        this.address = profile.getAddress();

        this.applyOn = provider.getApplyOn();
        this.approveOn = provider.getApproveOn();
        this.approveBy = provider.getApproveBy();
        this.isApproved = provider.isApproved();
        this.certificateNo = provider.getCertificateNo();
        this.practiceNo = provider.getPracticeNo();
        this.practiceLocation = provider.getPracticeLocation();
        this.primaryPracticeName = provider.getPrimaryPracticeName();
        this.professionalRank = provider.getProfessionalRank();
        this.specialties = provider.getSpecialties();
        this.skills = provider.getSkills();
    }

    public Employee toEmployee(){
        Employee employee = new Employee();
        Provider provider = new Provider();
        UserProfile profile = new UserProfile();

        employee.setId(this.id);
        employee.setPracticeId(this.practiceId);
        employee.setUserId(this.userId);
        employee.setManager(this.isManager);
        profile.setFamilyName(this.familyName);
        profile.setGivenName(this.givenName);
        profile.setDob(this.dob);
        profile.setSex(this.sex);
        profile.setEmail(this.email);
        profile.setMobile(this.mobile);
        profile.setProvinceId(this.provinceId);
        profile.setCityId(this.cityId);
        profile.setDistrictId(this.districtId);
        profile.setZip(this.zip);
        profile.setAddress(this.address);
        provider.setApplyOn(this.applyOn);
        provider.setApproveOn(this.approveOn);
        provider.setApproveBy(this.approveBy);
        provider.setApproved(this.isApproved);
        provider.setCertificateNo(this.certificateNo);
        provider.setPracticeNo(this.practiceNo);
        provider.setPracticeLocation(this.practiceLocation);
        provider.setPrimaryPracticeName(this.primaryPracticeName);
        provider.setProfessionalRank(this.professionalRank);
        provider.setSpecialties(this.specialties);
        provider.setSkills(this.skills);

        provider.setUserProfile(profile);
        employee.setProvider(provider);

        return employee;
    }
}
