package com.heng.org.service.impl;/**
 * @author shkstart
 * @create 2023-03-01 20:18
 */

import com.heng.base.service.impl.BaseServiceImpl;
import com.heng.org.dto.EmployeeRoleDTO;
import com.heng.org.domain.Employee;
import com.heng.org.mapper.EmployeeMapper;
import com.heng.org.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *@Auther:Jarvis
 *@Date:2023年03月2023/3/1日20:18
 *@Description:
 */
@Service
public class EmployeeServiceImpl extends BaseServiceImpl<Employee> implements IEmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;


    @Override
    public List<Employee> getAllEmployees() {
       return employeeMapper.getAllEmployees();
    }

    @Override
    public List<Long> getRoleByEmployeeId(Long employeeId) {
        return employeeMapper.getRoleByEmployeeId(employeeId);
    }

    @Override
    @Transactional
    public void saveRole(EmployeeRoleDTO dto) {
        //先删除原本的，在添加新改的
        employeeMapper.removeRole(dto.getEmployeeId());
        employeeMapper.saveRole(dto.getEmployeeId(),dto.getRoleId());
    }
}
