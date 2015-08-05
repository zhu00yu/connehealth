package com.connehealth.service;

import com.connehealth.security.TokenUtils;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.UriInfo;

/**
 * Created by zhuyu on 2015/8/5.
 */
public class BaseRestService {
    @Context
    protected UriInfo _uriInfo;
    @Context
    protected HttpHeaders _headers;
    public String getCurrentUserName(){
        return TokenUtils.getUserNameFromToken(_headers);
    }
}
