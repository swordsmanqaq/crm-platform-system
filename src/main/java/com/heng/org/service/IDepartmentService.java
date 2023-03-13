package com.heng.org.service;

import com.heng.base.service.IBaseService;
import com.heng.org.domain.Department;

import java.util.List;

/**
 * @author shkstart
 * @create 2023-03-01 18:52
 */

public interface IDepartmentService extends IBaseService<Department> {


    //查找部门树
    List<Department> getDeptTree();

    //查询所有部门信息
    List<Department> getAllDepartments();
}
