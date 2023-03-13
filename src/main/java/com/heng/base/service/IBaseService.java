package com.heng.base.service;

import com.heng.base.query.BaseQuery;
import com.heng.base.utils.PageList;

import java.io.Serializable;
import java.util.List;

/**
 * @author shkstart
 * @create 2023-03-10 14:06
 */
public interface IBaseService<T> {
    //新增
    void insert(T t);

    //删除   使用Serializable 但时候可以传入Long Long String都可以
    void remove(Serializable id);

    //批量删除
    void patchRemove(List<Long> ids);

    //修改
    void update(T t);

    //根据ID查询
    T loadById(Serializable id);

    //查询所有
    List<T> loadAll();

    //分页查询数据
    PageList<T> pageList(BaseQuery query);

}
