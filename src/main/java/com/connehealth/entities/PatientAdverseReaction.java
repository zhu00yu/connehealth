package com.connehealth.entities;

import java.util.Date;

/**
 * Created by zhuyu on 2015/8/27.
 */
public class PatientAdverseReaction extends AuditableEntity {
    private Long patientId;
    private Long allergyId;
    private Long adverseReactionId;
    private String reaction;


    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getAllergyId() {
        return allergyId;
    }

    public void setAllergyId(Long allergyId) {
        this.allergyId = allergyId;
    }

    public Long getAdverseReactionId() {
        return adverseReactionId;
    }

    public void setAdverseReactionId(Long adverseReactionId) {
        this.adverseReactionId = adverseReactionId;
    }

    public String getReaction() {
        return reaction;
    }

    public void setReaction(String reaction) {
        this.reaction = reaction;
    }
}
