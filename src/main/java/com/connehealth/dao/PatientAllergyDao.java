package com.connehealth.dao;

import com.connehealth.entities.PatientAllergy;
import com.connehealth.entities.PatientProblem;

import java.util.List;

/**
 * Created by zhuyu on 2015/8/7.
 */
public interface PatientAllergyDao {
    public List<PatientAllergy> getPatientAllergies();
    public List<PatientAllergy> getPatientAllergiesByPatient(Long patientId);

    public PatientAllergy getPatientAllergyById(Long id);

    public Long deletePatientAllergyById(Long id);

    public Long createPatientAllergy(PatientAllergy patientAllergy);

    public int updatePatientAllergy(PatientAllergy patientAllergy);

    public void deletePatientAllergies();

}
