package com.heng.shop.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.heng.base.domain.BaseDomain;
import com.heng.car.domain.Car;
import com.heng.org.domain.Shop;
import com.heng.user.domain.User;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author Jarvis-Smith
 * @since 2023-04-12
 */
@Data
public class ShopingCar extends BaseDomain{

    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 用户id-->前端传来的是logininfo的id，需要连表查
     */
    private Long userId;
    private User user;
    /**
     * 商品id
     */
    private Long carId;
    private Car car;
    /**
     * 数量
     */
    private Integer number;
    /**
     * 价格
     */
    private BigDecimal price;
    /**
     * 小计，即总价，单价乘数量
     */
    private BigDecimal subtotal;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    private Long shopId;
    private Shop shop;
    private Integer state;
}
