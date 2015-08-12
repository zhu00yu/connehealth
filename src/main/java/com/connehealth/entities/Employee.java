package com.connehealth.entities;

import javax.xml.bind.annotation.XmlRootElement;


/**
 * Created by zhuyu on 2015/8/10.
 */
@XmlRootElement
public class Employee extends AuditableEntity {
    private Long practiceId;
    private Long userId;
    private boolean isManager;

    private Practice practice;
    private Provider provider;


    public Long getPracticeId() {
        return practiceId;
    }

    public void setPracticeId(Long practiceId) {
        this.practiceId = practiceId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean isManager() {
        return isManager;
    }

    public void setManager(boolean isManager) {
        this.isManager = isManager;
    }

    public Practice getPractice() {
        return practice;
    }

    public void setPractice(Practice practice) {
        this.practice = practice;
        this.practiceId = practice.getId();
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
        this.userId = provider.getId();
    }
}
