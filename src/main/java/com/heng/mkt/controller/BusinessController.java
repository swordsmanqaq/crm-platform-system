package com.heng.mkt.controller;

import com.heng.base.utils.LoginContext;
import com.heng.mkt.dto.PayDepositDTO;
import com.heng.mkt.service.IBusinessService;
import com.heng.mkt.domain.Business;
import com.heng.mkt.query.BusinessQuery;
import com.heng.base.utils.PageList;
import com.heng.base.utils.AjaxResult;
import com.heng.org.domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/business")
public class BusinessController {
    @Autowired
    public IBusinessService businessService;


    /**
     * 保存和修改公用的
     * @param business 传递的实体
     * @return Ajaxresult转换结果
     */
    @PutMapping
    public AjaxResult addOrUpdate(@RequestBody Business business) {
        try {
            if ( business.getId() != null)
                businessService.update(business);
            else
                businessService.insert(business);
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
                businessService.remove(id);
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
                businessService.patchRemove(ids);
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
            Business business =businessService.loadById(id);
            return AjaxResult.me().setResultObj(business);
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
            List< Business> list = businessService.loadAll();
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
    public AjaxResult pageList(@RequestBody BusinessQuery query) {
        try {
            PageList<Business> pageList = businessService.pageList(query);
            return AjaxResult.me().setResultObj(pageList);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("获取分页数据失败！" + e.getMessage());
        }
    }


    /**
     * 缴纳定金请接口
     * @param dto
     * @return
     */
    @PostMapping ("/saveDeposit")
    public AjaxResult saveDeposit(@RequestBody PayDepositDTO dto, HttpServletRequest request) {
        try {
            Employee loginUser = LoginContext.getLoginUser(request);
            businessService.payDeposit(dto,loginUser);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("保存对象失败！" + e.getMessage());
        }
    }
}
