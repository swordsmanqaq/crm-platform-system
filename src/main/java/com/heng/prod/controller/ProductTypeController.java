package com.heng.prod.controller;

import com.heng.prod.service.IProductTypeService;
import com.heng.prod.domain.ProductType;
import com.heng.prod.query.ProductTypeQuery;
import com.heng.base.utils.PageList;
import com.heng.base.utils.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productType")
public class ProductTypeController {
    @Autowired
    public IProductTypeService productTypeService;


    /**
     * 保存和修改公用的
     * @param productType 传递的实体
     * @return Ajaxresult转换结果
     */
    @PutMapping
    public AjaxResult addOrUpdate(@RequestBody ProductType productType) {
        try {
            if ( productType.getId() != null)
                productTypeService.update(productType);
            else
                productTypeService.insert(productType);
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
                productTypeService.remove(id);
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
                productTypeService.patchRemove(ids);
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
            ProductType productType =productTypeService.loadById(id);
            return AjaxResult.me().setResultObj(productType);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("获取一个失败！" + e.getMessage());
        }
    }


    /**
    * 查看所有的信息
    * @return
    */
    @GetMapping
    public AjaxResult loadAll() {

        try {
            List< ProductType> list = productTypeService.loadAll();
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
    public AjaxResult pageList(@RequestBody ProductTypeQuery query) {
        try {
            PageList<ProductType> pageList = productTypeService.pageList(query);
            return AjaxResult.me().setResultObj(pageList);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("获取分页数据失败！" + e.getMessage());
        }
    }

    /**
     * 查找父产品
     * @return
     */
    @GetMapping("/first")
    public AjaxResult getFirstProductType(){
        try {
            List<ProductType> firstProducts = productTypeService.getFirstProductType();
            return AjaxResult.me().setResultObj(firstProducts);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("获取失败！" + e.getMessage());
        }
    }

    /**
     * 获取产品类型表
     * @return
     */
    @GetMapping("/tree")
    public AjaxResult getProdTree(){
        try {
            List<ProductType> typeTrees = productTypeService.getProdTree();
            return AjaxResult.me().setResultObj(typeTrees);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("查找产品类型树失败");
        }
    }

}
