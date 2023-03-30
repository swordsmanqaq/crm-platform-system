package com.heng.org.service;

import com.heng.org.domain.Shop;
import com.heng.base.service.IBaseService;
import com.heng.org.dto.ShopRegisterDTO;
import com.heng.org.dto.ShopRejectDTO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jarvis-Smith
 * @since 2023-03-27
 */
public interface IShopService extends IBaseService<Shop> {

    void shopSubmission(ShopRegisterDTO shopRegisterDTO);

//    void saveReject(Long id);

    void active(Long id);

    void auditCommit(ShopRejectDTO shopRejectDTO);

}
