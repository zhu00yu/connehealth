package com.connehealth.dao;

import com.connehealth.entities.PatientSocialHistory;

import java.util.List;

/**
 * Created by zhuyu on 2015/8/7.
 */
public interface PatientSocialHistoryDao {
    public List<PatientSocialHistory> getPatientSocialHistories();
    public List<PatientSocialHistory> getPatientSocialHistoriesByPatient(Long patientId);

    public PatientSocialHistory getPatientSocialHistoryById(Long id);

    public Long deletePatientSocialHistoryById(Long id);

    public Long createPatientSocialHistory(PatientSocialHistory patientSocialHistory);

    public int updatePatientSocialHistory(PatientSocialHistory patientSocialHistory);

    public void deletePatientSocialHistories();

}
