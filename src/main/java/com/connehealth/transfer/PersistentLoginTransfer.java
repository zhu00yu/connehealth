package com.connehealth.transfer;

import java.util.List;

/**
 * Created by zhuyu on 2015/8/13.
 */
public class PersistentLoginTransfer {
    private String userName;
    private Long practiceId;
    private String token;
    private List<String> authorities;

    public PersistentLoginTransfer(String userName, Long practiceId, String token, List<String> authorities){
        setUserName(userName);
        setPracticeId(practiceId);
        setToken(token);
        setAuthorities(authorities);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getPracticeId() {
        return practiceId;
    }

    public void setPracticeId(Long practiceId) {
        this.practiceId = practiceId;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }
}
