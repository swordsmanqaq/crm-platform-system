package com.heng.aftermarket.controller;

import com.heng.aftermarket.service.IGuaranteeItemService;
import com.heng.aftermarket.domain.GuaranteeItem;
import com.heng.aftermarket.query.GuaranteeItemQuery;
import com.heng.base.utils.PageList;
import com.heng.base.utils.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/guaranteeItem")
public class GuaranteeItemController {
    @Autowired
    public IGuaranteeItemService guaranteeItemService;


    /**
     * 保存和修改公用的
     * @param guaranteeItem 传递的实体
     * @return Ajaxresult转换结果
     */
    @PutMapping
    public AjaxResult addOrUpdate(@RequestBody GuaranteeItem guaranteeItem) {
        try {
            if ( guaranteeItem.getId() != null)
                guaranteeItemService.update(guaranteeItem);
            else
                guaranteeItemService.insert(guaranteeItem);
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
    @DeleteMapping(value = "/{id}")
    public AjaxResult remove(@PathVariable("id") Long id) {
        try {
                guaranteeItemService.remove(id);
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
                guaranteeItemService.patchRemove(ids);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("批量删除失败！" + e.getMessage());
        }
    }

    /**
    * 根据id获取
     * @param id
     * @return
    */
    @GetMapping("/{id}")
    public AjaxResult loadById(@PathVariable("id") Long id) {
        try {
            GuaranteeItem guaranteeItem =guaranteeItemService.loadById(id);
            return AjaxResult.me().setResultObj(guaranteeItem);
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
            List< GuaranteeItem> list = guaranteeItemService.loadAll();
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
    public AjaxResult pageList(@RequestBody GuaranteeItemQuery query) {
        try {
            PageList<GuaranteeItem> pageList = guaranteeItemService.pageList(query);
            return AjaxResult.me().setResultObj(pageList);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("获取分页数据失败！" + e.getMessage());
        }
    }
}
