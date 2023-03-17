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
public class ReceivablesRemark extends BaseDomain{

    private static final long serialVersionUID = 1L;

    private Long id;
    private String noteContent;
    private String createTime;
    private Long createBy;
    private String editTime;
    private Long editBy;
    /**
     * 0
     */
    private String editFlag;
    private Long receivablesId;
}
