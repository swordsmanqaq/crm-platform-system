package com.heng.auth.service.impl;/**
 * @author shkstart
 * @create 2023-03-06 13:12
 */

import com.heng.auth.domain.Permission;
import com.heng.auth.mapper.PermissionMapper;
import com.heng.auth.service.IPermissionService;
import com.heng.base.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *@Auther:Jarvis
 *@Date:2023年03月2023/3/6日13:12
 *@Description:
 */
@Service
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements IPermissionService {

    @Autowired
    private PermissionMapper permissionMapper;


    @Override
    public List<Permission> getTree() {
        return permissionMapper.getTree();
    }

    @Override
    public List<String> getSns() {
        return permissionMapper.getSns();
    }
}
