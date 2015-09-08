package com.connehealth.dao;

import com.connehealth.entities.PatientAllergy;
import com.connehealth.entities.PatientMedicalHistory;

import java.util.List;

/**
 * Created by zhuyu on 2015/8/7.
 */
public interface PatientMedicalHistoryDao {
    public List<PatientMedicalHistory> getPatientMedicalHistories();
    public List<PatientMedicalHistory> getPatientMedicalHistoriesByPatient(Long patientId);

    public PatientMedicalHistory getPatientMedicalHistoryById(Long id);

    public Long deletePatientMedicalHistoryById(Long id);

    public Long createPatientMedicalHistory(PatientMedicalHistory patientMedicalHistory);

    public int updatePatientMedicalHistory(PatientMedicalHistory patientMedicalHistory);

    public void deletePatientMedicalHistories();

}
