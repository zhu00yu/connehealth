package com.connehealth.entities;

import java.util.List;

/**
 * Created by zhuyu on 2015/8/5.
 */
public class User {
    private Long userId;
    private String userName;
    private List<String> authorities;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }
}
