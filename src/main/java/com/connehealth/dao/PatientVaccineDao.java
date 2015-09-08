package com.connehealth.dao;

import com.connehealth.entities.PatientMedication;
import com.connehealth.entities.PatientVaccine;

import java.util.List;

/**
 * Created by zhuyu on 2015/8/7.
 */
public interface PatientVaccineDao {
    public List<PatientVaccine> getPatientVaccines();
    public List<PatientVaccine> getPatientVaccinesByPatient(Long patientId);

    public PatientVaccine getPatientVaccineById(Long id);
/*
    public Patient getPatientByName(String name);
*/

    public Long deletePatientVaccineById(Long id);

    public Long createPatientVaccine(PatientVaccine patientVaccine);

    public int updatePatientVaccine(PatientVaccine patientVaccine);

    public void deletePatientVaccines();

}
