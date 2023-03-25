package com.heng.auth.mapper;

import com.heng.auth.domain.Role;
import com.heng.base.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author shkstart
 * @create 2023-03-01 14:36
 */
public interface RoleMapper extends BaseMapper<Role> {

    //保存权限
    //若mapper传多个参数，需要使用@Param注解
    void savePermission(@Param("roleId") Long roleId, @Param("permissionSns") List<String> permissionSns);

    //根据角色id删除
    void removeRoleById(Long roleId);

    //根据roleId获取sn
    List<String> getPermissionByRoleId(Long roleId);

    //根据roleId获取menu_id
    List<Long> getMenuByRoleId(Long roleId);

    void deleteRoleById(Long roleId);

    void saveMenu(@Param("roleId") Long roleId, @Param("menuId") List<Long> menuId);

    List<Long> getRoleIds();

}
