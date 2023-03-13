package com.heng.auth.controller;/**
 * @author shkstart
 * @create 2023-03-06 10:52
 */

import com.heng.auth.annotation.MyPermission;
import com.heng.auth.domain.Permission;
import com.heng.auth.query.PermissionQuery;
import com.heng.auth.service.IPermissionScanService;
import com.heng.auth.service.IPermissionService;
import com.heng.base.utils.AjaxResult;
import com.heng.base.utils.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *@Auther:Jarvis
 *@Date:2023年03月2023/3/6日10:52
 *@Description:
 */
@RestController
@RequestMapping("/permission")
@MyPermission(name = "权限管理", desc = "权限管理层")
public class PermissionController {

    @Autowired
    private IPermissionService permissionService;

    /**
     * 权限分页查询
     * @param query
     * @return
     */
    @PostMapping
    public AjaxResult pageList(@RequestBody PermissionQuery query){
        try {
            PageList<Permission> pageLists = permissionService.pageList(query);
            return AjaxResult.me().setResultObj(pageLists);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("权限分页查询失败");
        }
    }

    /**
     * 获取权限树
     * @return
     */
    @GetMapping("/tree")
    public AjaxResult getPermissionTree(){
        try {
            List<Permission> permissionTrees = permissionService.getTree();
            return AjaxResult.me().setResultObj(permissionTrees);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("获取权限树失败");
        }
    }

    @GetMapping("/sns")
    public AjaxResult getSns(){
        try {
            List<String> permissionSns = permissionService.getSns();
            return AjaxResult.me().setResultObj(permissionSns);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("查询权限标识失败");
        }
    }

}
