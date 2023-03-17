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
public class ReceivablesItem extends BaseDomain{

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private Long contractId;
    private Long customerId;
    private String customerName;
    private Long productId;
    private String productName;
    private BigDecimal price;
    /**
     * 现金  支付宝 微信
     */
    private Long paymethodId;
    private Date payTime;
    /**
     * 每期最后应付时间
     */
    private Date lastShouldPayTime;
    private Long receivablesId;
    /**
     * 待收   已收
     */
    private Integer state;
    /**
     * 收款人
     */
    private Long payee;
    private String payeeName;
}
