package com.connehealth.entities;

import java.util.Date;

/**
 * Created by zhuyu on 2015/8/13.
 */
public class PersistentLogin {
    private String userName;
    private String series;
    private String token;
    private Date lastUsed;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(Date lastUsed) {
        this.lastUsed = lastUsed;
    }
}
