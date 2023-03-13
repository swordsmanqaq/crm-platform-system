package com.heng.auth.mapper;

import com.heng.auth.domain.Menu;
import com.heng.auth.query.MenuQuery;
import com.heng.base.mapper.BaseMapper;

import java.io.Serializable;
import java.util.List;

/**
 * @author shkstart
 * @create 2023-03-08 09:41
 */
public interface MenuMapper extends BaseMapper<Menu> {

    List<Menu> getTree();

    List<Long> getMenuIds();

    List<Menu> getMenuTree(Long loginUserId);

}
