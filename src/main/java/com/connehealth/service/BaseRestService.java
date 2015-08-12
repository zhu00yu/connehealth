package com.connehealth.service;

import com.connehealth.dao.UserDao;
import com.connehealth.entities.AuditableEntity;
import com.connehealth.entities.User;
import com.connehealth.security.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.UriInfo;
import java.util.Date;

/**
 * Created by zhuyu on 2015/8/5.
 */
public class BaseRestService {
    @Context
    protected UriInfo _uriInfo;
    @Context
    protected HttpHeaders _headers;

    @Autowired
    protected UserDao userDao;
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public String getCurrentUserName(HttpHeaders headers){
        return TokenUtils.getUserNameFromToken(headers);
    }

    public User getCurrentUser(String name){
        User user = userDao.getUserByUserName(name);
        return user;
    }
    public User getCurrentUser(HttpHeaders headers){
        User user = getCurrentUser(getCurrentUserName(headers));
        return user;
    }
    public <T> T setAuditInfoForCreator(T entity, HttpHeaders headers){
        User user = getCurrentUser(headers);
        if (entity instanceof AuditableEntity && user != null){
            ((AuditableEntity) entity).setCreateBy(user.getUserId());
            ((AuditableEntity) entity).setCreateOn(new Date());
        }

        return entity;
    }
    public <T> T setAuditInfoForModifer(T entity, HttpHeaders headers){
        User user = getCurrentUser(headers);
        if (entity instanceof AuditableEntity && user != null){
            ((AuditableEntity) entity).setModifyBy(user.getUserId());
            ((AuditableEntity) entity).setModifyOn(new Date());
        }

        return entity;
    }
}
