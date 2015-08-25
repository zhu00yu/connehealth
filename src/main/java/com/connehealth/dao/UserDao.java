package com.connehealth.dao;

import com.connehealth.entities.PersistentLogin;
import com.connehealth.entities.User;
import com.connehealth.entities.UserProfile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by zhuyu on 2015/8/5.
 */
public interface UserDao {
    public List<User> getUsers();

    public User getUserById(Long id);
    public User getUserByUserName(String userName);
    public List<String> getAuthorityByUserName(String userName);

    public PersistentLogin getLatestLoginedUser(String userName);
    public Long createLatestLoginedUser(PersistentLogin userLogin);

    public Long createUser(User user);
    public Long createAuthority(@Param(value = "userName")String userName, @Param(value = "authority")String authority);
}
