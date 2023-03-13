package com.heng.base.service.impl;/**
 * @author shkstart
 * @create 2023-03-10 14:08
 */

import com.heng.base.mapper.BaseMapper;
import com.heng.base.query.BaseQuery;
import com.heng.base.service.IBaseService;
import com.heng.base.utils.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 *@Auther:Jarvis
 *@Date:2023年03月2023/3/10日14:08
 *@Description:
 */

@Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
public class BaseServiceImpl <T> implements IBaseService<T>{

    @Autowired
    private BaseMapper<T> baseMapper;

    @Override
    @Transactional
    public void insert(T t) {
        baseMapper.insert(t);
    }

    @Override
    @Transactional
    public void remove(Serializable id) {
        baseMapper.remove(id);
    }

    @Override
    @Transactional
    public void patchRemove(List<Long> ids) {
        baseMapper.patchRemove(ids);
    }

    @Override
    @Transactional
    public void update(T t) {
        baseMapper.update(t);
    }

    @Override
    public T loadById(Serializable id) {
        return baseMapper.loadById(id);
    }

    @Override
    public List<T> loadAll() {
        return baseMapper.loadAll();
    }

    @Override
    public PageList<T> pageList(BaseQuery query) {
        //查询总条数
        long total = baseMapper.loadTotal(query);
        if (total > 0){
            List<T> rows = baseMapper.pageList(query);
            return new PageList<T>(total, rows);
        }
        return new PageList<>();
    }


}
