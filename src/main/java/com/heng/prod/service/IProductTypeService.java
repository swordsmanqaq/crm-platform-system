package com.heng.prod.service;

import com.heng.prod.domain.ProductType;
import com.heng.base.service.IBaseService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jarvis-Smith
 * @since 2023-03-14
 */
public interface IProductTypeService extends IBaseService<ProductType> {

    List<ProductType> getFirstProductType();

}
