package com.heng.mkt.domain;

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
 * @since 2023-03-14
 */
@Data
public class ClueActivity extends BaseDomain{

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long clueId;
    private Long activityId;
}
