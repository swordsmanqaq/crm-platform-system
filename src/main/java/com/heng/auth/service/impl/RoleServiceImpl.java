package com.heng.auth.service.impl;/**
 * @author shkstart
 * @create 2023-03-06 15:16
 */

import com.heng.auth.domain.Role;
import com.heng.auth.dto.RoleMenuDTO;
import com.heng.auth.dto.RolePermissionDTO;
import com.heng.auth.mapper.RoleMapper;
import com.heng.auth.service.IRoleService;
import com.heng.base.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *@Auther:Jarvis
 *@Date:2023年03月2023/3/6日15:16
 *@Description:
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role> implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    @Transactional
    public void savePermission(RolePermissionDTO dto) {
        //若存在角色，且有权限，先删除原来的，再保存新的进去
        roleMapper.removeRoleById(dto.getRoleId());
        roleMapper.savePermission(dto.getRoleId(),dto.getPermissionSns());
    }

    @Override
    public List<String> getPermissionByRoleId(Long roleId) {
        return roleMapper.getPermissionByRoleId(roleId);
    }

    @Override
    public List<Long> getMenuByRoleId(Long roleId) {
        return roleMapper.getMenuByRoleId(roleId);
    }

    @Override
    @Transactional
    public void saveMenu(RoleMenuDTO dto) {
        //若存在角色，且有菜单设置，先删除原来的，再保存新的进去
        roleMapper.deleteRoleById(dto.getRoleId());
        roleMapper.saveMenu(dto.getRoleId(),dto.getMenuId());
    }

    @Override
    public List<Long> getRoleIds() {
        return roleMapper.getRoleIds();
    }

}
