package com.connehealth.dao;

import com.connehealth.entities.Patient;
import com.connehealth.entities.PatientProblem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by zhuyu on 2015/8/7.
 */
public interface PatientProblemDao {
    public List<PatientProblem> getPatientProblems();
    public List<PatientProblem> getPatientProblemsByPatient(Long patientId);

    public PatientProblem getPatientProblemById(Long id);
/*
    public Patient getPatientByName(String name);
*/

    public Long deletePatientProblemById(Long id);

    public Long createPatientProblem(PatientProblem patientProblem);

    public int updatePatientProblem(PatientProblem patientProblem);

    public void deletePatientProblems();

}
