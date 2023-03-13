package com.heng.auth.mapper;

import com.heng.auth.domain.Role;
import com.heng.auth.query.RoleQuery;
import com.heng.base.mapper.BaseMapper;
import com.heng.base.utils.PageList;
import com.heng.org.domain.Department;
import com.heng.org.query.DepartmentQuery;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
 * @author shkstart
 * @create 2023-03-01 14:36
 */
public interface RoleMapper extends BaseMapper<Role> {

    //保存权限
    //若mapper传多个参数，需要使用@Param注解
    void savePermission(@Param("roleId") Integer roleId, @Param("permissionSns") List<String> permissionSns);

    //根据角色id删除
    void removeRoleById(Integer roleId);

    //根据roleId获取sn
    List<String> getPermissionByRoleId(Integer roleId);

    //根据roleId获取menu_id
    List<Integer> getMenuByRoleId(Integer roleId);

    void deleteRoleById(Integer roleId);

    void saveMenu(@Param("roleId") Integer roleId, @Param("menuId") List<Integer> menuId);

    List<Integer> getRoleIds();

}
