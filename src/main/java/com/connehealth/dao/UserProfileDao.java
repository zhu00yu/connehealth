package com.connehealth.dao;

import com.connehealth.entities.UserProfile;

import java.util.List;

/**
 * Created by zhuyu on 2015/8/5.
 */
public interface UserProfileDao {
    public List<UserProfile> getUserProfiles();

    public UserProfile getUserProfileById(Long id);
    public UserProfile getUserProfileByUserName(String userName);

    public Long deleteUserProfileById(Long id);

    public Long createUserProfile(UserProfile userProfile);

    public int updateUserProfile(UserProfile userProfile);

    public void deleteUserProfiles();
}
