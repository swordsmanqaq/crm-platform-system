package com.heng.sys.service.impl;

import com.heng.sys.domain.Dictionary;
import com.heng.sys.service.IDictionaryService;
import com.heng.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class DictionaryServiceImpl extends BaseServiceImpl<Dictionary> implements IDictionaryService {

//    @Override
//    @Transactional
//    public void removeByParentId(Long parentId) {
//        dictionaryitemMapper.removeByParentId(parentId);
//    }
//
//    @Override
//    @Transactional
//    public void patchRemoveByParentId(List<Long> parentIds) {
//        dictionaryitemMapper.patchRemoveByParentId(parentIds);
//    }
}
