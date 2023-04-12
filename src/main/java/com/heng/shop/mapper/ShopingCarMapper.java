package com.heng.shop.mapper;

import com.heng.shop.domain.ShopingCar;
import com.heng.base.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Jarvis-Smith
 * @since 2023-04-12
 */
public interface ShopingCarMapper extends BaseMapper<ShopingCar> {

    Long loadShopingCarsTotalByUserId(Long loginId);

    List<ShopingCar> loadShopingCarsByUserId(Long loginId);

    List<Long> loadAllIdsByUserId(Long loginId);

    ShopingCar loadByCarId(Long carId);
}
