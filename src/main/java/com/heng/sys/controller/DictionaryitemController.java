package com.heng.sys.controller;

import com.heng.auth.annotation.MyPermission;
import com.heng.sys.service.IDictionaryitemService;
import com.heng.sys.domain.Dictionaryitem;
import com.heng.sys.query.DictionaryitemQuery;
import com.heng.base.utils.PageList;
import com.heng.base.utils.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dictionaryitem")
public class DictionaryitemController {
    @Autowired
    public IDictionaryitemService dictionaryitemService;


    /**
     * 保存和修改公用的
     * @param dictionaryitem  传递的实体
     * @return Ajaxresult转换结果
     */
    @PutMapping
    public AjaxResult addOrUpdate(@RequestBody Dictionaryitem dictionaryitem){
        try {
            if( dictionaryitem.getId()!=null)
                dictionaryitemService.update(dictionaryitem);
            else
                dictionaryitemService.insert(dictionaryitem);
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
            dictionaryitemService.remove(id);
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
    @MyPermission(name = "菜单批量删除管理", desc = "菜单批量删除")
    public AjaxResult patchRemove(@RequestBody List<Long> ids) {
        try {
            dictionaryitemService.patchRemove(ids);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("很抱歉，批量删除失败");
        }
    }
	
    //获取用户
    @GetMapping("/{id}")
    public AjaxResult loadById(@PathVariable("id")Long id)
    {
        try {
            Dictionaryitem dictionaryitem = dictionaryitemService.loadById(id);
            return AjaxResult.me().setResultObj(dictionaryitem);
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
            List< Dictionaryitem> list = dictionaryitemService.loadAll();
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
    @PostMapping
    public AjaxResult pageList(@RequestBody DictionaryitemQuery query)
    {
        try {
            PageList<Dictionaryitem> pageList = dictionaryitemService.pageList(query);
            return AjaxResult.me().setResultObj(pageList);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("获取分页数据失败！"+e.getMessage());
        }
    }
}
