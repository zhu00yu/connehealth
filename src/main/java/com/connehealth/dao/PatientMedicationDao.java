package com.connehealth.dao;

import com.connehealth.entities.PatientMedication;
import com.connehealth.entities.PatientProblem;

import java.util.List;

/**
 * Created by zhuyu on 2015/8/7.
 */
public interface PatientMedicationDao {
    public List<PatientMedication> getPatientMedications();
    public List<PatientMedication> getPatientMedicationsByPatient(Long patientId);

    public PatientMedication getPatientMedicationById(Long id);
/*
    public Patient getPatientByName(String name);
*/

    public Long deletePatientMedicationById(Long id);

    public Long createPatientMedication(PatientMedication patientMedication);

    public int updatePatientMedication(PatientMedication patientMedication);

    public void deletePatientMedications();

}
