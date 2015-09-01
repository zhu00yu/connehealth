package com.connehealth.dao;

import com.connehealth.entities.Employee;
import com.connehealth.entities.Practice;
import com.connehealth.entities.Provider;
import com.connehealth.entities.UserProfile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by zhuyu on 2015/8/7.
 */
public interface EmployeeDao {
    public List<Employee> getEmployees();
    public List<Employee> getPracticeEmployees(Long practiceId);
    public List<UserProfile> getFreeUsers(@Param(value = "practiceId")Long practiceId, @Param(value = "name") String name);

    public Employee getEmployeeById(Long id);
    public Employee getEmployeeByUser(@Param(value = "practiceId")Long practiceId, @Param(value = "userId")Long userId);
/*
    public Employee getEmployeeByName(String name);
*/

    public Long deleteEmployeeById(Long id);

    public Long createEmployee(Employee Employee);

    public int updateEmployee(Employee Employee);

    public void deleteEmployees();

    public Provider getProviderById(Long id);
    public Practice getPracticeById(Long id);
}
