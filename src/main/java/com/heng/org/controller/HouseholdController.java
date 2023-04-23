package com.heng.org.controller;

import com.heng.auth.annotation.MyPermission;
import com.heng.org.dto.HouseholdRoleDTO;
import com.heng.org.service.IHouseholdService;
import com.heng.org.domain.Household;
import com.heng.org.query.HouseholdQuery;
import com.heng.base.utils.PageList;
import com.heng.base.utils.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/household")
@MyPermission(name = "住户管理", desc = "住户管理层")
public class HouseholdController {
    @Autowired
    public IHouseholdService householdService;


    /**
     * 保存和修改公用的
     * @param household  传递的实体
     * @return Ajaxresult转换结果
     */
    @PostMapping("/save")
    @MyPermission(name = "住户新增/修改管理", desc = "住户新增/修改")
    public AjaxResult addOrUpdate(@RequestBody Household household){
        try {
            if( household.getId()!=null)
                householdService.update(household);
            else
                householdService.insert(household);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("保存对象失败！"+e.getMessage());
        }
    }
    /**
    * 删除对象信息
    * @param id
    * @return
    */
    @DeleteMapping(value="/{id}")
    @MyPermission(name = "住户删除管理", desc = "住户删除")
    public AjaxResult remove(@PathVariable("id") Long id){
        try {
            householdService.remove(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！"+e.getMessage());
        }
    }

    /**
     * 批量删除
     * @param ids
     * @return
    */
    @PatchMapping
    @MyPermission(name = "住户批量删除管理", desc = "住户批量删除")
    public AjaxResult patchRemove(@RequestBody List<Long> ids)
    {
        try {
                householdService.patchRemove(ids);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("批量删除失败！"+e.getMessage());
        }
    }

    /**
     * 根据id获取
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public AjaxResult loadById(@PathVariable("id")Long id)
    {
        try {
            Household household = householdService.loadById(id);
            return AjaxResult.me().setResultObj(household);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("获取一个失败！"+e.getMessage());
        }
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @GetMapping
    public AjaxResult loadAll(){

        try {
            List< Household> list = householdService.loadAll();
            return AjaxResult.me().setResultObj(list);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("获取所有失败！"+e.getMessage());
        }
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping("/page")
    public AjaxResult pageList(@RequestBody HouseholdQuery query)
    {
        try {
            PageList<Household> pageList = householdService.pageList(query);
            return AjaxResult.me().setResultObj(pageList);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("获取分页数据失败！"+e.getMessage());
        }
    }

    /**
     * 根据householdId获取role_id做数据回显
     * @param householdId
     * @return
     */
    @GetMapping("/role/{householdId}")
    public AjaxResult getRoleByRoleId(@PathVariable("householdId") Long householdId){
        try {
            List<Long> roleIds = householdService.getRoleByHouseholdId(householdId);
            return AjaxResult.me().setResultObj(roleIds);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("获取employee_id失败");
        }
    }

    /**
     * 设置角色的提交接口
     * @param dto
     * @return
     */
    @PostMapping("/role")
    @MyPermission(name = "设置住户角色管理",desc = "设置住户角色")
    public AjaxResult saveRole(@RequestBody HouseholdRoleDTO dto){
        try {
            householdService.saveRole(dto);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("角色保存失败");
        }
    }
}
