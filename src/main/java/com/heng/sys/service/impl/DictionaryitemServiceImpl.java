package com.heng.sys.service.impl;

import com.heng.sys.domain.Dictionaryitem;
import com.heng.sys.mapper.DictionaryitemMapper;
import com.heng.sys.service.IDictionaryitemService;
import com.heng.base.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jarvis-Smith
 * @since 2023-03-13
 */
@Service
public class DictionaryitemServiceImpl extends BaseServiceImpl<Dictionaryitem> implements IDictionaryitemService {

    @Autowired
    private DictionaryitemMapper dictionaryitemMapper;

    @Override
    public List<Dictionaryitem> loadItemById(Long id) {
        return dictionaryitemMapper.loadItemById(id);
    }

    @Override
    public List<Dictionaryitem> getPayModel(String sn) {
        return dictionaryitemMapper.getPayModel(sn);
    }
}
