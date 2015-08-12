package com.connehealth.dao;

import com.connehealth.entities.Practice;
import com.connehealth.entities.Provider;
import com.connehealth.entities.UserProfile;

import java.util.List;

/**
 * Created by zhuyu on 2015/8/7.
 */
public interface ProviderDao {
    public List<Provider> getProviders();

    public Provider getProviderById(Long id);
/*
    public Provider getProviderByName(String name);
*/

    public Long deleteProviderById(Long id);

    public Long createProvider(Provider provider);

    public int updateProvider(Provider provider);

    public void deleteProviders();

    public UserProfile getUserProfileById(Long id);
}
