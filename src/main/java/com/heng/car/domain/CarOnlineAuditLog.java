package com.heng.car.domain;

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
 * @since 2023-03-29
 */
@Data
public class CarOnlineAuditLog extends BaseDomain{

    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 消息id
     */
    private Long carId;
    /**
     * 状态 0失败 1成功
     */
    private Integer state;
    /**
     * 审核人 如果为null系统审核
     */
    private Long auditId;
    /**
     * 审核时间
     */
    private Date auditTime;
    /**
     * 备注
     */
    private String note;
}
