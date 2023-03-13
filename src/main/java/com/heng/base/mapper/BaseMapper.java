package com.heng.base.mapper;

import com.heng.base.query.BaseQuery;
import com.heng.org.domain.Department;
import com.heng.org.query.DepartmentQuery;
import org.apache.ibatis.annotations.Mapper;

import java.io.Serializable;
import java.util.List;

/**
 * @author shkstart
 * @create 2023-03-10 13:10
 */
public interface BaseMapper<T> {

    //新增
    void insert(T t);

    //删除   使用Serializable 但时候可以传入Long Integer String都可以
    void remove(Serializable id);

    //批量删除
    void patchRemove(List<Integer> ids);

    //修改
    void update(T t);

    //根据ID查询
    T loadById(Serializable id);

    //查询所有
    List<T> loadAll();

    //分页查询数据
    List<T> pageList(BaseQuery query);

    //记录总条数
    int loadTotal(BaseQuery query);

}
