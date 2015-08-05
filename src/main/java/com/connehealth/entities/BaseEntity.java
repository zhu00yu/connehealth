package com.connehealth.entities;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Created by zhuyu on 2015/8/5.
 */
@XmlRootElement
public class BaseEntity {
    private Long id;
    private Date createOn;
    private int status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateOn() {
        return createOn;
    }

    public void setCreateOn(Date createOn) {
        this.createOn = createOn;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
