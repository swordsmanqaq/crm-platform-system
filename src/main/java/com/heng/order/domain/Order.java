package com.heng.order.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.heng.base.domain.BaseDomain;
import com.heng.mkt.domain.Business;
import com.heng.prod.domain.Product;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author Jarvis-Smith
 * @since 2023-03-16
 */
@Data
public class Order extends BaseDomain{

    private static final long serialVersionUID = 1L;

    private Long id;
    private String sn;
    private Long businessId;
    private Business business;
    private String businessName;
    private Long productId;
    private Product product;
    private String productName;
    /**
     * 产品的价格+营销活动的力度
     */
    private BigDecimal price;
    private Long customerId;
    private String customerName;
    /**
     * 0-代付款 1- 已付定金 2-已交尾款 3-已完成 -1取消
     */
    private Integer state;
}
