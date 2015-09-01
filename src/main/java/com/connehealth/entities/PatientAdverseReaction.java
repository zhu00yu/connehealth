package com.connehealth.entities;

import java.util.Date;

/**
 * Created by zhuyu on 2015/8/27.
 */
public class PatientAdverseReaction extends AuditableEntity {
    private Long patientId;
    private Long allergenId;
    private Long adverseReactionId;


    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getAllergenId() {
        return allergenId;
    }

    public void setAllergenId(Long allergenId) {
        this.allergenId = allergenId;
    }

    public Long getAdverseReactionId() {
        return adverseReactionId;
    }

    public void setAdverseReactionId(Long adverseReactionId) {
        this.adverseReactionId = adverseReactionId;
    }
}
