package com.heng.org.mapper;

import com.heng.org.domain.Shop;
import com.heng.base.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Jarvis-Smith
 * @since 2023-03-27
 */
public interface ShopMapper extends BaseMapper<Shop> {

    Shop loadByName(String name);
}
