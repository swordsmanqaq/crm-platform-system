package com.heng.order.domain;

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
public class OrderActivity extends BaseDomain{

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long orderId;
    private Long activityId;
}
