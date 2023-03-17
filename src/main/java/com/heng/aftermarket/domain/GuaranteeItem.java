package com.heng.aftermarket.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.heng.base.domain.BaseDomain;
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
public class GuaranteeItem extends BaseDomain{

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long productId;
    private String productName;
    private Long orderId;
    private String orderSn;
    private String customerName;
    private Long customerId;
    private Long guaranteeId;
    /**
     * 收费  免费-数据字典
     */
    private Integer isPay;
    private BigDecimal paymoney;
}
