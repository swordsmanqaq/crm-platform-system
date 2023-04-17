package com.heng.auth.controller;/**
 * @author shkstart
 * @create 2023-03-08 09:21
 */

import com.heng.auth.annotation.MyPermission;
import com.heng.auth.domain.Menu;
import com.heng.auth.query.MenuQuery;
import com.heng.auth.service.IMenuService;
import com.heng.base.utils.AjaxResult;
import com.heng.base.utils.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 *@Auther:Jarvis
 *@Date:2023年03月2023/3/8日09:21
 *@Description:
 */
@RestController
@RequestMapping("/menu")
@MyPermission(name = "菜单管理", desc = "菜单管理层")
public class MenuController {

    @Autowired
    private IMenuService iMenuService;

    /**
     * 新增和修改
     * @param menu
     * @return
     */
    @PostMapping("/save")
    @MyPermission(name = "菜单新增/修改管理", desc = "菜单新增/修改")
    public AjaxResult addOrUpdate(@RequestBody Menu menu) {
        try {
            //先判断是否有id
            if (Objects.nonNull(menu.getId())) {
                //有id进行修改操作
                iMenuService.update(menu);
            } else {

                iMenuService.insert(menu);
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
    @MyPermission(name = "菜单删除管理", desc = "菜单删除")
    public AjaxResult deleteById(@PathVariable("id") Long id) {
        try {
            iMenuService.remove(id);

        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("很抱歉，删除失败");
        }
        return AjaxResult.me();

    }

    @GetMapping("/{id}")
    public AjaxResult getById(@PathVariable("id") Long id) {
        try {
            Menu menu = iMenuService.loadById(id);
            return AjaxResult.me().setResultObj(menu);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("很抱歉，查询失败");
        }
    }

    /**
     * 获取所有
     * @return
     */
    @GetMapping
    public AjaxResult getAll() {
        try {
            List<Menu> menus = iMenuService.loadAll();
            return AjaxResult.me().setResultObj(menus);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("查询失败");
        }
    }

    /**
     * 分页查询
     * @return
     */
    @PostMapping("/page")
    public AjaxResult getPages(@RequestBody MenuQuery query) {
        try {
            PageList<Menu> pageList = iMenuService.pageList(query);
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
    @MyPermission(name = "菜单批量删除管理", desc = "菜单批量删除")
    public AjaxResult patchRemove(@RequestBody List<Long> ids) {
        try {
            iMenuService.patchRemove(ids);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("很抱歉，批量删除失败");
        }
    }


    /**
     * 获取菜单树
     * @return
     */
    @GetMapping("/tree")
    public AjaxResult getPermissionTree(){
        try {
            List<Menu> menuTrees = iMenuService.getTree();
            return AjaxResult.me().setResultObj(menuTrees);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("获取菜单树失败");
        }
    }

    /**
     * 获取菜单id
     * @return
     */
    @GetMapping("/ids")
    public AjaxResult getSns(){
        try {
            List<Long> menuIds = iMenuService.getMenuIds();
            return AjaxResult.me().setResultObj(menuIds);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("查询失败");
        }
    }


    /**
     * 根据用户ID菜单树
     * @return
     */
    @GetMapping("/tree/{loginUserId}")
    public AjaxResult getTree(@PathVariable("loginUserId") Long loginUserId){
        try {
            List<Menu> menuTree = iMenuService.getMenuTree(loginUserId);
            return AjaxResult.me().setResultObj(menuTree);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("获取菜单树失败");
        }
    }


}
