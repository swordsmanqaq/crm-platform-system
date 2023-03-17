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
public class ContractRemark extends BaseDomain{

    private static final long serialVersionUID = 1L;

    private Long id;
    private String noteContent;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private String createBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date editTime;
    private Long editBy;
    /**
     * 0
     */
    private String editFlag;
    private Long contractId;
}
