package com.heng.org.service;

import com.heng.org.domain.Iccard;
import com.heng.base.service.IBaseService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jarvis-Smith
 * @since 2023-04-19
 */
public interface IIccardService extends IBaseService<Iccard> {

    void saveICLoss(Long userId);

}
