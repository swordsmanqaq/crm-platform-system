package com.heng.mkt.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.heng.base.domain.BaseDomain;
import com.heng.sys.domain.Dictionaryitem;
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
public class Activity extends BaseDomain{

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    /**
     * 活动的优惠方式 关联数据字典明细表
     */
    private Long preferentialMethodId;
    private Dictionaryitem discount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;
    /**
     * 类型 1=营销活动 2=市场活动
     */
    private Integer type;
    /**
     * 满足某个价格
     */
    private BigDecimal enoughPrice;
    /**
     * 折扣价格
     */
    private BigDecimal costPrice;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 谁创建的活动
     */
    private String createBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date editTime;
    /**
     * 谁最后一次修改
     */
    private String editBy;
}
