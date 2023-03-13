package com.heng.auth.controller;/**
 * @author shkstart
 * @create 2023-03-06 13:50
 */

import com.heng.auth.annotation.MyPermission;
import com.heng.auth.domain.Role;
import com.heng.auth.dto.RoleMenuDTO;
import com.heng.auth.dto.RolePermissionDTO;
import com.heng.auth.query.RoleQuery;
import com.heng.auth.service.IRoleService;
import com.heng.base.utils.AjaxResult;
import com.heng.base.utils.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 *@Auther:Jarvis
 *@Date:2023年03月2023/3/6日13:50
 *@Description:
 */
@RestController
@RequestMapping("/role")
@MyPermission(name = "角色管理",desc = "角色权限管理")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    /**
     * 新增/修改
     * @param role
     * @return
     */
    @PostMapping("/save")
    @MyPermission(name = "角色新增/修改管理",desc = "角色新增/修改权限管理")
    public AjaxResult addOrUpdate(@RequestBody Role role) {
        try {
            //先判断是否有id
            if (Objects.nonNull(role.getId())) {
                //有id进行修改操作
                roleService.update(role);
            } else {
                roleService.insert(role);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("很抱歉，提交失败");
        }
        return AjaxResult.me();
    }

    /**
     * 删除操作
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @MyPermission(name = "角色删除管理",desc = "角色删除权限管理")
    public AjaxResult deleteById(@PathVariable("id") Integer id) {
        try {
            roleService.remove(id);

        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("很抱歉，删除失败");
        }
        return AjaxResult.me();

    }

    /**
     * 根据id查询角色信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public AjaxResult getById(@PathVariable("id") Integer id) {
        try {
            Role role = roleService.loadById(id);
            return AjaxResult.me().setResultObj(role);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("很抱歉，查询失败");
        }
    }

    /**
     * 查询所有角色
     * @return
     */
    @GetMapping
    public AjaxResult getAll() {
        try {
            List<Role> roles = roleService.loadAll();
            return AjaxResult.me().setResultObj(roles);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("很抱歉，查询失败");
        }
    }

    /**
     * 分页查询
     * @return
     */
    @PostMapping("/page")
    public AjaxResult getPages(@RequestBody RoleQuery roleQuery) {
        try {
            PageList<Role> pageList = roleService.pageList(roleQuery);
            return AjaxResult.me().setResultObj(pageList);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("分页查询失败");
        }
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @PatchMapping
    @MyPermission(name = "角色批量删除管理",desc = "角色批量删除权限管理")
    public AjaxResult patchRemove(@RequestBody List<Integer> ids) {
        try {
            roleService.patchRemove(ids);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("很抱歉，批量删除失败");
        }
    }

    /**
     * 设置权限的提交接口
     * @param dto
     * @return
     */
    @PostMapping("/permission")
    @MyPermission(name = "设置角色权限管理",desc = "设置角色权限")
    public AjaxResult savePermission(@RequestBody RolePermissionDTO dto){
        try {
            roleService.savePermission(dto);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("权限保存失败");
        }
    }

    /**
     * 根据roleId获取sn做数据回显
     * @param roleId
     * @return
     */
    @GetMapping("/permission/{roleId}")
    public AjaxResult getPermissionByRoleId(@PathVariable("roleId") Integer roleId){
        try {
            List<String> snsKeys = roleService.getPermissionByRoleId(roleId);
            return AjaxResult.me().setResultObj(snsKeys);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("获取sn失败");
        }
    }


    /**
     * 根据roleId获取menu_id做数据回显
     * @param roleId
     * @return
     */
    @GetMapping("/menu/{roleId}")
    public AjaxResult getMenuByRoleId(@PathVariable("roleId") Integer roleId){
        try {
            List<Integer> menuIds = roleService.getMenuByRoleId(roleId);
            return AjaxResult.me().setResultObj(menuIds);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("获取role_id失败");
        }
    }

    /**
     * 设置菜单的提交接口
     * @param dto
     * @return
     */
    @PostMapping("/menu")
    @MyPermission(name = "设置角色菜单管理",desc = "设置角色菜单")
    public AjaxResult saveMenu(@RequestBody RoleMenuDTO dto){
        try {
            roleService.saveMenu(dto);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("菜单保存失败");
        }
    }


    /**
     * 获取角色id
     * @return
     */
    @GetMapping("/ids")
    public AjaxResult getIds(){
        try {
            List<Integer> roleIds = roleService.getRoleIds();
            return AjaxResult.me().setResultObj(roleIds);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("查询失败");
        }
    }

}
