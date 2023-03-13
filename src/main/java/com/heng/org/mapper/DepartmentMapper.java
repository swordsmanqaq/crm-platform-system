package com.heng.org.mapper;

import com.heng.base.mapper.BaseMapper;
import com.heng.org.domain.Department;
import com.heng.org.query.DepartmentQuery;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

/**
 * @author shkstart
 * @create 2023-03-01 14:36
 */

public interface DepartmentMapper extends BaseMapper<Department> {


    //查找部门树
    List<Department> getDeptTree();

    //查询所有部门信息
    List<Department> getAllDepartments();
}
