package com.heng.org.controller;

import com.heng.auth.annotation.MyPermission;
import com.heng.org.service.IPersonEntryAndExitRecordsService;
import com.heng.org.domain.PersonEntryAndExitRecords;
import com.heng.org.query.PersonEntryAndExitRecordsQuery;
import com.heng.base.utils.PageList;
import com.heng.base.utils.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/personEntryAndExitRecords")
@MyPermission(name = "外来人员记录管理", desc = "外来人员记录管理层")
public class PersonEntryAndExitRecordsController {
    @Autowired
    public IPersonEntryAndExitRecordsService personEntryAndExitRecordsService;


    /**
     * 保存和修改公用的
     * @param personEntryAndExitRecords  传递的实体
     * @return Ajaxresult转换结果
     */
    @PostMapping("/save")
    @MyPermission(name = "外来人员新增/修改管理", desc = "外来人员新增/修改")
    public AjaxResult addOrUpdate(@RequestBody PersonEntryAndExitRecords personEntryAndExitRecords){
        try {
            if( personEntryAndExitRecords.getId()!=null)
                personEntryAndExitRecordsService.update(personEntryAndExitRecords);
            else
                personEntryAndExitRecordsService.insert(personEntryAndExitRecords);
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
    @MyPermission(name = "外来人员删除管理", desc = "外来人员删除")
    public AjaxResult remove(@PathVariable("id") Long id){
        try {
            personEntryAndExitRecordsService.remove(id);
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
    @MyPermission(name = "外来人员批量删除管理", desc = "外来人员批量删除")
    public AjaxResult patchRemove(@RequestBody List<Long> ids)
    {
        try {
                personEntryAndExitRecordsService.patchRemove(ids);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("批量删除失败！"+e.getMessage());
        }
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public AjaxResult loadById(@PathVariable("id")Long id)
    {
        try {
            PersonEntryAndExitRecords personEntryAndExitRecords = personEntryAndExitRecordsService.loadById(id);
            return AjaxResult.me().setResultObj(personEntryAndExitRecords);
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
            List< PersonEntryAndExitRecords> list = personEntryAndExitRecordsService.loadAll();
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
    public AjaxResult pageList(@RequestBody PersonEntryAndExitRecordsQuery query)
    {
        try {
            PageList<PersonEntryAndExitRecords> pageList = personEntryAndExitRecordsService.pageList(query);
            return AjaxResult.me().setResultObj(pageList);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("获取分页数据失败！"+e.getMessage());
        }
    }
}
