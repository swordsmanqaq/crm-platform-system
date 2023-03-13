package com.heng.org.service;

import com.heng.base.service.IBaseService;
import com.heng.org.dto.EmployeeRoleDTO;
import com.heng.org.domain.Employee;

import java.io.Serializable;
import java.util.List;

/**
 * @author shkstart
 * @create 2023-03-01 20:17
 */
public interface IEmployeeService extends IBaseService<Employee> {


    //查询所有员工
    List<Employee> getAllEmployees();


    //根据employeeId查询RoleId
    List<Integer> getRoleByEmployeeId(Integer employeeId);

    //保存角色列表
    void saveRole(EmployeeRoleDTO dto);
}
