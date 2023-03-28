package com.heng.org.domain;

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
 * @since 2023-03-27
 */
@Data
public class ShopAuditLog extends BaseDomain{

    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 1=审核成功 0=审核失败
     */
    private Integer state;
    /**
     * 店铺的id
     */
    private Long shopId;
    /**
     * 审核人的id
     */
    private Long auditId;
    /**
     * 审核时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date auditTime;
    /**
     * 备注
     */
    private String note;
}
