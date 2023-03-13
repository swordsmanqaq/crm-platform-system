package com.heng.org.mapper;

import com.heng.base.mapper.BaseMapper;
import com.heng.org.domain.Employee;
import com.heng.org.query.EmployeeQuery;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
 * @author shkstart
 * @create 2023-03-01 14:36
 */

public interface EmployeeMapper extends BaseMapper<Employee> {

    //查询所有员工
    List<Employee> getAllEmployees();

    //根据用户姓名查询用户信息
    Employee loadByUsername(String username);

    //根据employeeId查询RoleId
    List<Integer> getRoleByEmployeeId(Integer employeeId);

    void removeRole(Integer employeeId);

    void saveRole(@Param("employeeId") Integer employeeId, @Param("roleId") List<Integer> roleId);
}
