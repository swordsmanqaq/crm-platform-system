package com.heng.shop.service;

import com.heng.shop.domain.ShopingCar;
import com.heng.base.service.IBaseService;
import com.heng.shop.dto.CarNumberDTO;
import com.heng.shop.dto.ChangeStateDTO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jarvis-Smith
 * @since 2023-04-12
 */
public interface IShopingCarService extends IBaseService<ShopingCar> {

    //添加购物车
    void addShppingCar(ShopingCar shopingCar);

    //获取当前用户购物车总数
    Long getShopingCarsTotal(Long loginId);

    //获取当前用户购物车的所有信息
    List<ShopingCar> getShopingCarsByUser(Long loginId);

    //清空购物车
    void emptyShoppingCar(Long loginId);

    //车辆数量增加
    void increase(CarNumberDTO dto);

    //车辆数量减少
    void decrease(CarNumberDTO dto);

    //改变选中状态
    void changeCarState(ChangeStateDTO dto);
}
