package com.heng.sys.service;

import com.heng.sys.domain.Dictionaryitem;
import com.heng.base.service.IBaseService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jarvis-Smith
 * @since 2023-03-13
 */
public interface IDictionaryitemService extends IBaseService<Dictionaryitem> {

    List<Dictionaryitem> loadItemById(Long id);

    List<Dictionaryitem> getPayModel(String sn);
}
