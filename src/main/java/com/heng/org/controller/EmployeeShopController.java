package com.heng.org.controller;

import com.heng.org.service.IEmployeeShopService;
import com.heng.org.domain.EmployeeShop;
import com.heng.org.query.EmployeeShopQuery;
import com.heng.base.utils.PageList;
import com.heng.base.utils.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employeeShop" )
public class EmployeeShopController {
    @Autowired
    public IEmployeeShopService employeeShopService;


    /**
     * 保存和修改公用的
     * @param employeeShop 传递的实体
     * @return Ajaxresult转换结果
     */
    @PutMapping
    public AjaxResult addOrUpdate(@RequestBody EmployeeShop employeeShop) {
        try {
            if ( employeeShop.getId() != null)
                employeeShopService.update(employeeShop);
            else
                employeeShopService.insert(employeeShop);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("保存对象失败！" + e.getMessage());
        }
    }

    /**
    * 删除对象信息
    * @param id
    * @return
    */
    @DeleteMapping(value = "/{id}" )
    public AjaxResult remove(@PathVariable("id" ) Long id) {
        try {
                employeeShopService.remove(id);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！" + e.getMessage());
        }
    }

    /**
     * 批量删除
     * @param ids
     * @return
    */
    @PatchMapping
    public AjaxResult patchRemove(@RequestBody List<Long> ids) {
        try {
                employeeShopService.patchRemove(ids);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("批量删除失败！" + e.getMessage());
        }
    }

    /**
    * 根据Id获取用户
    * @param id
    * @return
    */
    @GetMapping("/{id}" )
    public AjaxResult loadById(@PathVariable("id" ) Long id) {
        try {
            EmployeeShop employeeShop =employeeShopService.loadById(id);
            return AjaxResult.me().setResultObj(employeeShop);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("获取一个失败！" + e.getMessage());
        }
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @GetMapping
    public AjaxResult loadAll() {

        try {
            List< EmployeeShop> list = employeeShopService.loadAll();
            return AjaxResult.me().setResultObj(list);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("获取所有失败！" + e.getMessage());
        }
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping
    public AjaxResult pageList(@RequestBody EmployeeShopQuery query) {
        try {
            PageList<EmployeeShop> pageList = employeeShopService.pageList(query);
            return AjaxResult.me().setResultObj(pageList);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("获取分页数据失败！" + e.getMessage());
        }
    }
}
