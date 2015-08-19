package com.connehealth.transfer;

import java.util.List;

public class TokenTransfer
{

    private final String token;
    private List<String> authorities;


    public TokenTransfer(String token, List<String> authorities)
    {
        this.token = token;
        this.authorities = authorities;
    }


    public String getToken()
    {
        return this.token;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }
}
