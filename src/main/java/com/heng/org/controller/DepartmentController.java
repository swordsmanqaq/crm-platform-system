package com.heng.org.controller;/**
 * @author shkstart
 * @create 2023-03-01 19:24
 */

import com.heng.auth.annotation.MyPermission;
import com.heng.base.utils.AjaxResult;
import com.heng.base.utils.PageList;
import com.heng.org.domain.Department;
import com.heng.org.query.DepartmentQuery;
import com.heng.org.service.IDepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * @Auther:Jarvis
 * @Date:2023年03月2023/3/1日19:24
 * @Description:
 */
@RestController
@RequestMapping("/department")
@Api(value = "部门的API", description = "部门的CRUD功能")
@MyPermission(name = "部门管理",desc = "部门权限管理")
public class DepartmentController {

    @Autowired
    private IDepartmentService departmentService;

    /**
     * 新增和修改方法
     *
     * @param department
     * @return
     */
    @PostMapping("/save")
    @ApiOperation(value = "新增和修改的提交方法")
    @MyPermission(name = "部门新增/修改管理",desc = "部门新增/修改权限管理")
    public AjaxResult addOrUpdate(@RequestBody Department department) {
        try {
            //先判断是否有id
            if (Objects.nonNull(department.getId())) {
                //有id进行修改操作
                departmentService.update(department);
            } else {

                departmentService.insert(department);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("很抱歉，提交失败" + e.getMessage());
        }
        return AjaxResult.me();
    }

    /**
     * 删除操作
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据id删除方法")
    @MyPermission(name = "部门删除管理",desc = "部门删除权限管理")
    public AjaxResult deleteById(@PathVariable("id") Integer id) {
        try {
            departmentService.remove(id);

        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("很抱歉，删除失败");
        }
        return AjaxResult.me();

    }

    /**
     * 根据id查询员工信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询方法")
    public AjaxResult getById(@PathVariable("id") Integer id) {
        try {
            Department department = departmentService.loadById(id);
            return AjaxResult.me().setResultObj(department);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("很抱歉，查询失败");
        }
    }

    /**
     * 查询所有部门
     *
     * @return
     */
    @GetMapping
    @ApiOperation(value = "查询所有信息的方法")
    public AjaxResult getAll() {
        try {
            List<Department> departments = departmentService.loadAll();
            return AjaxResult.me().setResultObj(departments);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("很抱歉，查询失败");
        }
    }

    /**
     * 分页查询
     * @return
     */
    @PostMapping("/page")
    public AjaxResult getPages(@RequestBody DepartmentQuery query) {
        try {
            PageList<Department> pageList = departmentService.pageList(query);
            return AjaxResult.me().setResultObj(pageList);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("分页查询失败");
        }
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @PatchMapping
    @MyPermission(name = "部门批量删除管理",desc = "部门批量删除权限管理")
    public AjaxResult patchRemove(@RequestBody List<Integer> ids) {
        try {
            departmentService.patchRemove(ids);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("很抱歉，批量删除失败" + e.getMessage());
        }
    }

    /**
     * 部门树，查一级部门和二级部门
     * @return
     */
    @GetMapping("/tree")
    public AjaxResult getDeptTree(){
        try {
            List<Department> deptTrees = departmentService.getDeptTree();
            return AjaxResult.me().setResultObj(deptTrees);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("查找部门树结构失败");
        }
    }

    /**
     * 查询所有部门信息
     * @return
     */
    @GetMapping("/emp")
    public AjaxResult getAllDepartments(){
        try {
            List<Department> departmentList = departmentService.getAllDepartments();
            return AjaxResult.me().setResultObj(departmentList);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("查询失败");
        }
    }


}
