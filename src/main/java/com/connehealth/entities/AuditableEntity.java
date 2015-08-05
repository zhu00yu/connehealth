package com.connehealth.entities;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Created by zhuyu on 2015/8/5.
 */
@XmlRootElement
public class AuditableEntity extends BaseEntity {
    private Long createBy;
    private Long modifyBy;
    private Date modifyOn;

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Long getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(Long modifyBy) {
        this.modifyBy = modifyBy;
    }

    public Date getModifyOn() {
        return modifyOn;
    }

    public void setModifyOn(Date modifyOn) {
        this.modifyOn = modifyOn;
    }
}
