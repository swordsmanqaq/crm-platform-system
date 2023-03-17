package com.heng.finance.domain;

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
public class Deposit extends BaseDomain{

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private Long customerId;
    private String customerName;
    private Long deposit;
    private Date payTime;
    private Long productId;
    private String productName;
    private Long businessId;
    private String businessName;
    /**
     * 已退还 已缴纳 
     */
    private Integer state;
    private Long orderId;
    private String orderSn;
}
