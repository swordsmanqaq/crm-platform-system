package com.heng.org.controller;

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
public class HouseholdController {
    @Autowired
    public IHouseholdService householdService;


    /**
     * 保存和修改公用的
     * @param household  传递的实体
     * @return Ajaxresult转换结果
     */
    @PutMapping
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
}
