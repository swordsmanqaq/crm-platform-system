package com.heng.order.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class Contract extends BaseDomain{

    private static final long serialVersionUID = 1L;

    private Long id;
    private String sn;
    private Long orderId;
    private String orderSn;
    private Long customerId;
    private String customerName;
    private BigDecimal price;
    /**
     * 定金
     */
    private BigDecimal deposit;
    private String content;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    private Integer state;
}
