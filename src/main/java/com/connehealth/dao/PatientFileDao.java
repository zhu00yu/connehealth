package com.connehealth.dao;

import com.connehealth.entities.PatientFile;

import java.util.List;

/**
 * Created by zhuyu on 2015/8/7.
 */
public interface PatientFileDao {
    public List<PatientFile> getPatientFiles();

    public PatientFile getPatientFileById(Long id);
    public PatientFile getPatientFileByName(String name);

    public Long deletePatientFileById(Long id);

    public Long createPatientFile(PatientFile patientFile);

    public int updatePatientFile(PatientFile patientFile);

    public void deletePatientFiles();
}
