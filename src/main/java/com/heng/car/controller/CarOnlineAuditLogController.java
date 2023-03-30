package com.heng.car.controller;

import com.heng.car.service.ICarOnlineAuditLogService;
import com.heng.car.domain.CarOnlineAuditLog;
import com.heng.car.query.CarOnlineAuditLogQuery;
import com.heng.base.utils.PageList;
import com.heng.base.utils.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carOnlineAuditLog" )
public class CarOnlineAuditLogController {
    @Autowired
    public ICarOnlineAuditLogService carOnlineAuditLogService;


    /**
     * 保存和修改公用的
     * @param carOnlineAuditLog 传递的实体
     * @return Ajaxresult转换结果
     */
    @PutMapping
    public AjaxResult addOrUpdate(@RequestBody CarOnlineAuditLog carOnlineAuditLog) {
        try {
            if ( carOnlineAuditLog.getId() != null)
                carOnlineAuditLogService.update(carOnlineAuditLog);
            else
                carOnlineAuditLogService.insert(carOnlineAuditLog);
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
                carOnlineAuditLogService.remove(id);
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
                carOnlineAuditLogService.patchRemove(ids);
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
            CarOnlineAuditLog carOnlineAuditLog =carOnlineAuditLogService.loadById(id);
            return AjaxResult.me().setResultObj(carOnlineAuditLog);
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
            List< CarOnlineAuditLog> list = carOnlineAuditLogService.loadAll();
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
    public AjaxResult pageList(@RequestBody CarOnlineAuditLogQuery query) {
        try {
            PageList<CarOnlineAuditLog> pageList = carOnlineAuditLogService.pageList(query);
            return AjaxResult.me().setResultObj(pageList);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("获取分页数据失败！" + e.getMessage());
        }
    }
}
