package com.heng.auth.mapper;

import com.heng.auth.domain.Permission;
import com.heng.auth.query.PermissionQuery;
import com.heng.base.mapper.BaseMapper;

import java.util.List;

/**
 * @author shkstart
 * @create 2023-03-05 16:27
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    //批量插入
    void batchInsert(List<Permission> permissions);

    //删除
    void removeAll();

    //查询权限树
    List<Permission> getTree();

    //查询所有权限标识
    List<String> getSns();

    //根据用户ID获取所有权限
    List<String> getAllPermissionByEmployeeId(Integer employeeId);


}
