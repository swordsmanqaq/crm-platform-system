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
public class Receivables extends BaseDomain{

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long contractId;
    private Long customerId;
    private String customerName;
    private Long productId;
    private String productName;
    private BigDecimal price;
    /**
     * 全款 分期期数 从数据字典查询
     */
    private Long paymentMethodId;
    private Date createTime;
    private Date updateTime;
}
