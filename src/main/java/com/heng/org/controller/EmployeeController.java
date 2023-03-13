package com.heng.org.controller;/**
 * @author shkstart
 * @create 2023-03-01 20:22
 */

import com.heng.auth.annotation.MyPermission;
import com.heng.org.dto.EmployeeRoleDTO;
import com.heng.base.utils.AjaxResult;
import com.heng.base.utils.PageList;
import com.heng.org.domain.Employee;
import com.heng.org.query.EmployeeQuery;
import com.heng.org.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 *@Auther:Jarvis
 *@Date:2023年03月2023/3/1日20:22
 *@Description:
 */
@RestController
@RequestMapping("/employee")
@MyPermission(name = "员工管理",desc = "员工权限管理")
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;

    /**
     * 查询所有员工
     * @return
     */
    @GetMapping
    public AjaxResult getAllEmployee(){
        try {
            List<Employee> employees = employeeService.getAllEmployees();
            return AjaxResult.me().setResultObj(employees);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("查询员工失败");
        }
    }

    /**
     * 分页查询
     * @param employeeQuery
     * @return
     */
    @PostMapping("/page")
    public AjaxResult getEmptyPages(@RequestBody EmployeeQuery employeeQuery){
        try {
            PageList<Employee> pageLists= employeeService.pageList(employeeQuery);
            return AjaxResult.me().setResultObj(pageLists);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("分页查询失败");
        }
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @MyPermission(name = "员工删除管理",desc = "员工删除权限管理")
    public AjaxResult deleteById(@PathVariable("id") Integer id) {
        try {
            employeeService.remove(id);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("删除失败");
        }
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @PatchMapping
    @MyPermission(name = "员工批量删除管理",desc = "员工批量删除权限管理")
    public AjaxResult patchDeleteEmployee(@RequestBody List<Integer> ids){
        try {
            employeeService.patchRemove(ids);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("批量删除失败");
        }
    }

    /**
     * 编辑提交
     * @param employee
     * @return
     */
    @PostMapping("/save")
    @MyPermission(name = "员工新增/修改管理",desc = "员工新增/修改权限管理")
    public AjaxResult saveEmployee(@RequestBody Employee employee){
        try {
            //根据id分新增和修改
            if (Objects.nonNull(employee.getId())){
                //修改方法
                employeeService.update(employee);
            }else {
                employeeService.insert(employee);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("提交失败");
        }
        return AjaxResult.me();

    }


    /**
     * 根据employee_id获取role_id做数据回显
     * @param employeeId
     * @return
     */
    @GetMapping("/role/{employeeId}")
    public AjaxResult getRoleByRoleId(@PathVariable("employeeId") Integer employeeId){
        try {
            List<Integer> roleIds = employeeService.getRoleByEmployeeId(employeeId);
            return AjaxResult.me().setResultObj(roleIds);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("获取employee_id失败");
        }
    }

    /**
     * 设置角色的提交接口
     * @param dto
     * @return
     */
    @PostMapping("/role")
    @MyPermission(name = "设置员工角色管理",desc = "设置员工角色")
    public AjaxResult saveRole(@RequestBody EmployeeRoleDTO dto){
        try {
            employeeService.saveRole(dto);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("角色保存失败");
        }
    }


}
