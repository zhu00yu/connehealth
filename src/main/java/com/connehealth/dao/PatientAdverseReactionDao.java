package com.connehealth.dao;

import com.connehealth.entities.PatientAdverseReaction;
import com.connehealth.entities.PatientAllergy;

import java.util.List;

/**
 * Created by zhuyu on 2015/8/7.
 */
public interface PatientAdverseReactionDao {
    public List<PatientAdverseReaction> getPatientAdverseReactions();
    public List<PatientAdverseReaction> getPatientAdverseReactionsByPatient(Long patientId);
    public List<PatientAdverseReaction> getPatientAdverseReactionsByAllergy(Long allergyId);

    public PatientAdverseReaction getPatientAdverseReactionById(Long id);


    public Long createPatientAdverseReaction(PatientAdverseReaction patientAdverseReaction);

    public int updatePatientAdverseReaction(PatientAdverseReaction patientAdverseReaction);

    public Long deletePatientAdverseReactionById(Long id);
    public Long deletePatientAdverseReactionByAllergy(Long allergyId);
    public void deletePatientAdverseReactions();

}
