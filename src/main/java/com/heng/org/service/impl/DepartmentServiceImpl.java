package com.heng.org.service.impl;/**
 * @author shkstart
 * @create 2023-03-01 18:53
 */

import com.heng.base.service.impl.BaseServiceImpl;
import com.heng.org.domain.Department;
import com.heng.org.mapper.DepartmentMapper;
import com.heng.org.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *@Auther:Jarvis
 *@Date:2023年03月2023/3/1日18:53
 *@Description:
 */
@Service
public class DepartmentServiceImpl extends BaseServiceImpl<Department> implements IDepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    @Transactional
    public void insert(Department department) {
        department.setCreate_time(new Date());
        departmentMapper.insert(department);
        //部门的path = 父部门的path+/+自己部门的ID
        String path = "";
        if (Objects.nonNull(department.getDepartment()) && Objects.nonNull(department.getDepartment().getId())) {
            Department department1 = departmentMapper.loadById(department.getDepartment().getId());
            path = department1.getPath();
        }
        //加上自己的id值
        path = path + "/" + department.getId();
        department.setPath(path);
        //重新更新数据
        departmentMapper.update(department);
    }

    @Override
    @Transactional
    public void update(Department department) {
        String path = "";
        if (Objects.nonNull(department.getDepartment()) && Objects.nonNull(department.getDepartment().getId())) {
            Department department1 = departmentMapper.loadById(department.getDepartment().getId());
            path = department1.getPath();
        }
        path = path + "/" + department.getId();
        department.setPath(path);
        department.setUpdate_time(new Date());
        departmentMapper.update(department);
    }



    @Override
    public List<Department> getDeptTree() {
        return departmentMapper.getDeptTree();
    }

    @Override
    public List<Department> getAllDepartments() {
        return departmentMapper.getAllDepartments();
    }

}
