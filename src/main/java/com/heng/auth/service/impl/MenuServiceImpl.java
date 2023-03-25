package com.heng.auth.service.impl;/**
 * @author shkstart
 * @create 2023-03-08 09:33
 */

import com.heng.auth.domain.Menu;
import com.heng.auth.mapper.MenuMapper;
import com.heng.auth.service.IMenuService;
import com.heng.base.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *@Auther:Jarvis
 *@Date:2023年03月2023/3/8日09:33
 *@Description:
 */
@Service
public class MenuServiceImpl extends BaseServiceImpl<Menu> implements IMenuService {

    @Autowired
    private MenuMapper menuMapper;


    @Override
    public List<Menu> getTree() {
        return menuMapper.getTree();

    }

    @Override
    public List<Long> getMenuIds() {
        return menuMapper.getMenuIds();
    }

    @Override
    public List<Menu> getMenuTree(Long loginUserId) {
        return menuMapper.getMenuTree(loginUserId);
    }

}
