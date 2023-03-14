package com.heng.prod.mapper;

import com.heng.prod.domain.ProductType;
import com.heng.base.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Jarvis-Smith
 * @since 2023-03-14
 */
public interface ProductTypeMapper extends BaseMapper<ProductType> {

    List<ProductType> getFirstProductType();

}
