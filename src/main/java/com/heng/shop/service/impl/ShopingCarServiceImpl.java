package com.heng.shop.service.impl;

import com.heng.shop.domain.ShopingCar;
import com.heng.shop.dto.CarNumberDTO;
import com.heng.shop.dto.ChangeStateDTO;
import com.heng.shop.mapper.ShopingCarMapper;
import com.heng.shop.service.IShopingCarService;
import com.heng.base.service.impl.BaseServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jarvis-Smith
 * @since 2023-04-12
 */
@Service
public class ShopingCarServiceImpl extends BaseServiceImpl<ShopingCar> implements IShopingCarService {

    @Autowired
    private ShopingCarMapper shopingCarMapper;

    /**
     * 添加购物车
     * @param shopingCar
     */
    @Override
    @Transactional
    public void addShppingCar(ShopingCar shopingCar) {
        ShopingCar shopingcar = shopingCarMapper.loadByCarId(shopingCar.getCarId());
        if (Objects.isNull(shopingcar)){
            //表示是第一次将车辆信息添加到购物车
            //新增购物车数据
            shopingCar.setCreateTime(new Date());
            //默认不选中
            shopingCar.setState(0);
            shopingCarMapper.insert(shopingCar);
        }else {
            //表示已经有该车辆信息，只需要改变数量和总价即可
            shopingcar.setNumber(shopingcar.getNumber() + 1);
            BigDecimal multiply = shopingcar.getPrice().multiply(BigDecimal.valueOf(shopingcar.getNumber()));
            shopingcar.setSubtotal(multiply);
            shopingCarMapper.update(shopingcar);
        }
    }

    /**
     * 获取当前用户购物车总数
     * @param loginId
     * @return
     */
    @Override
    @Transactional
    public Long getShopingCarsTotal(Long loginId) {
        return shopingCarMapper.loadShopingCarsTotalByUserId(loginId);
    }

    /**
     * 获取当前用户购物车信息
     * @param loginId
     * @return
     */
    @Override
    @Transactional
    public List<ShopingCar> getShopingCarsByUser(Long loginId) {
        return shopingCarMapper.loadShopingCarsByUserId(loginId);
    }

    /**
     * 清空购物车
     * @param loginId
     */
    @Override
    @Transactional
    public void emptyShoppingCar(Long loginId) {
        //先查处当前用户的所有购物车信息的id
        List<Long> ids = shopingCarMapper.loadAllIdsByUserId(loginId);
        shopingCarMapper.patchRemove(ids);
    }

    /**
     * 数量增加
     * @param dto
     */
    @Override
    @Transactional
    public void increase(CarNumberDTO dto) {
        ShopingCar shopingCar = shopingCarMapper.loadById(dto.getId());
        shopingCar.setNumber(dto.getCarnumber());
        BigDecimal multiply = shopingCar.getPrice().multiply(BigDecimal.valueOf(dto.getCarnumber()));
        shopingCar.setSubtotal(multiply);
        shopingCarMapper.update(shopingCar);
    }

    /**
     * 数量减少
     * @param dto
     */
    @Override
    @Transactional
    public void decrease(CarNumberDTO dto) {
        ShopingCar shopingCar = shopingCarMapper.loadById(dto.getId());
        shopingCar.setNumber(dto.getCarnumber());
        BigDecimal multiply = shopingCar.getPrice().multiply(BigDecimal.valueOf(dto.getCarnumber()));
        shopingCar.setSubtotal(multiply);
        shopingCarMapper.update(shopingCar);
    }

    /**
     * 改变选中状态
     * @param dto
     */
    @Override
    @Transactional
    public void changeCarState(ChangeStateDTO dto) {
        ShopingCar shopingCar = shopingCarMapper.loadById(dto.getId());
        shopingCar.setState(dto.getState());
        shopingCarMapper.update(shopingCar);
    }

}
