package com.heng.org.domain;

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
 * @since 2023-04-19
 */
@Data
public class Iccard extends BaseDomain{

    private static final long serialVersionUID = 1L;

    private Long id;
    private String uid;
    /**
     * 发送给的住户
     */
    private Long userId;
    private Household household;
    /**
     * IC卡类型
     */
    private String type;
    /**
     * 0是未分配 1是正常 -1是挂失
     */
    private Long state;
}
