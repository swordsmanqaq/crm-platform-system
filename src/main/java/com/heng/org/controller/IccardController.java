package com.heng.org.controller;

import com.heng.auth.annotation.MyPermission;
import com.heng.org.service.IIccardService;
import com.heng.org.domain.Iccard;
import com.heng.org.query.IccardQuery;
import com.heng.base.utils.PageList;
import com.heng.base.utils.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/iccard")
@MyPermission(name = "IC卡管理", desc = "IC卡管理层")
public class IccardController {
    @Autowired
    public IIccardService iccardService;


    /**
     * 保存和修改公用的
     *
     * @param iccard 传递的实体
     * @return Ajaxresult转换结果
     */
    @PutMapping
    @MyPermission(name = "IC卡新增/修改管理", desc = "IC卡新增/修改")
    public AjaxResult addOrUpdate(@RequestBody Iccard iccard) {
        try {
            if (iccard.getId() != null)
                iccardService.update(iccard);
            else
                iccardService.insert(iccard);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("保存对象失败！" + e.getMessage());
        }
    }

    /**
     * 删除对象信息
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public AjaxResult remove(@PathVariable("id") Long id) {
        try {
            iccardService.remove(id);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！" + e.getMessage());
        }
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @PatchMapping
    @MyPermission(name = "IC卡删除管理", desc = "IC卡删除")
    public AjaxResult patchRemove(@RequestBody List<Long> ids) {
        try {
            iccardService.patchRemove(ids);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("批量删除失败！" + e.getMessage());
        }
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public AjaxResult loadById(@PathVariable("id") Long id) {
        try {
            Iccard iccard = iccardService.loadById(id);
            return AjaxResult.me().setResultObj(iccard);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("获取一个失败！" + e.getMessage());
        }
    }


    /**
     * 查看所有的员工信息
     *
     * @return
     */
    @GetMapping
    public AjaxResult loadAll() {

        try {
            List<Iccard> list = iccardService.loadAll();
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
    @PostMapping("/page")
    public AjaxResult pageList(@RequestBody IccardQuery query) {
        try {
            PageList<Iccard> pageList = iccardService.pageList(query);
            return AjaxResult.me().setResultObj(pageList);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("获取分页数据失败！" + e.getMessage());
        }
    }

    /**
     * 卡片挂失提交
     * @param userId
     * @return
     */
    @PostMapping("/loss/{userId}")
    @MyPermission(name = "IC卡挂失管理", desc = "IC卡挂失")
    public AjaxResult saveICLoss(@PathVariable("userId") Long userId) {
        try {
            iccardService.saveICLoss(userId);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("卡片挂失提交失败！" + e.getMessage());
        }
    }

    /**
     * 卡片分配提交
     * @param iccard
     * @return
     */
    @PostMapping("/allocation")
    @MyPermission(name = "IC卡分配管理", desc = "IC卡分配")
    public AjaxResult allocationIC(@RequestBody Iccard iccard) {
        try {
            iccardService.allocationIC(iccard);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("分配失败！" + e.getMessage());
        }
    }

}
