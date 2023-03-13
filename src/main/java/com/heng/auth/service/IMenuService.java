package com.heng.auth.service;

import com.heng.auth.domain.Menu;
import com.heng.auth.query.MenuQuery;
import com.heng.base.service.IBaseService;
import com.heng.base.utils.PageList;
import com.heng.org.domain.Department;
import com.heng.org.query.DepartmentQuery;

import java.io.Serializable;
import java.util.List;

/**
 * @author shkstart
 * @create 2023-03-08 09:27
 */
public interface IMenuService extends IBaseService<Menu> {


    List<Menu> getTree();

    List<Long> getMenuIds();

    List<Menu> getMenuTree(Long loginUserId);
}
