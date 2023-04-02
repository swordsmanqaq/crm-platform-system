package com.heng.car.controller;

import com.heng.base.utils.LoginContext;
import com.heng.car.domain.CarResources;
import com.heng.car.service.ICarDetailService;
import com.heng.car.domain.CarDetail;
import com.heng.car.query.CarDetailQuery;
import com.heng.base.utils.PageList;
import com.heng.base.utils.AjaxResult;
import com.heng.org.domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/carDetail" )
public class CarDetailController {
    @Autowired
    public ICarDetailService carDetailService;


    /**
     * 保存和修改公用的
     * @param carDetail 传递的实体
     * @return Ajaxresult转换结果
     */
    @PutMapping
    public AjaxResult addOrUpdate(@RequestBody CarDetail carDetail, HttpServletRequest request) {
        try {
            Employee loginUser = LoginContext.getLoginUser(request);
            carDetailService.saveCarReport(carDetail,loginUser);
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
                carDetailService.remove(id);
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
                carDetailService.patchRemove(ids);
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
            CarDetail carDetail =carDetailService.loadById(id);
            return AjaxResult.me().setResultObj(carDetail);
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
            List< CarDetail> list = carDetailService.loadAll();
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
    public AjaxResult pageList(@RequestBody CarDetailQuery query) {
        try {
            PageList<CarDetail> pageList = carDetailService.pageList(query);
            return AjaxResult.me().setResultObj(pageList);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("获取分页数据失败！" + e.getMessage());
        }
    }

    /**
     * 根据carId获取资源做图片回显
     * @param carId
     * @return
     */
    @GetMapping("/carId/{carId}" )
    public AjaxResult getCarResourcesByCarId(@PathVariable("carId") Long carId) {
        try {
            CarDetail carDetail = carDetailService.loadByCarId(carId);
            return AjaxResult.me().setResultObj(carDetail);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("获取一个失败！" + e.getMessage());
        }
    }

}
