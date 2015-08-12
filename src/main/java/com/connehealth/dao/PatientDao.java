package com.connehealth.dao;

import com.connehealth.entities.Patient;
import com.connehealth.entities.UserProfile;

import java.util.List;

/**
 * Created by zhuyu on 2015/8/7.
 */
public interface PatientDao {
    public List<Patient> getPatients();

    public Patient getPatientById(Long id);
/*
    public Patient getPatientByName(String name);
*/

    public Long deletePatientById(Long id);

    public Long createPatient(Patient patient);

    public int updatePatient(Patient patient);

    public void deletePatients();

}
