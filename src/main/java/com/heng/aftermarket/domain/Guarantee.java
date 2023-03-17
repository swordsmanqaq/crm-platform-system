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
public class Guarantee extends BaseDomain{

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long productId;
    private String productName;
    private Long orderId;
    private String orderSn;
    private Long customerId;
    private String customerName;
    private Date startTime;
    private Date endTime;
    /**
     * 1：保修期内；0：保修期外
     */
    private Integer state;
}
