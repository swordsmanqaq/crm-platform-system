package com.heng.auth.service;

import com.heng.auth.domain.Permission;
import com.heng.base.service.IBaseService;

import java.util.List;

/**
 * @author shkstart
 * @create 2023-03-06 13:12
 */
public interface IPermissionService extends IBaseService<Permission> {

    List<Permission> getTree();

    //查询所有权限
    List<String> getSns();

}
