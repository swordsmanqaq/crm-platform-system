package com.heng.auth.service;

import com.heng.auth.domain.Role;
import com.heng.auth.dto.RoleMenuDTO;
import com.heng.auth.dto.RolePermissionDTO;
import com.heng.base.service.IBaseService;

import java.util.List;

/**
 * @author shkstart
 * @create 2023-03-06 15:07
 */
public interface IRoleService extends IBaseService<Role> {

    //权限保存
    void savePermission(RolePermissionDTO dto);

    //根据roleId获取sn
    List<String> getPermissionByRoleId(Integer roleId);

    //根据roleId获取menu_id
    List<Integer> getMenuByRoleId(Integer roleId);

    //菜单保存
    void saveMenu(RoleMenuDTO dto);

    //获得角色id
    List<Integer> getRoleIds();

}
