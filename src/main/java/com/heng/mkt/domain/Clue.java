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
public class Clue extends BaseDomain{

    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 全名
     */
    private String fullName;
    /**
     * 称呼
     */
    private String appellation;
    private String company;
    private String job;
    private String email;
    private String phone;
    private String mphone;
    private String address;
    /**
     * 客户来源，关联向数据字典明细
     */
    private Long source;
    /**
     * 营销人员，关联向员工表
     */
    private Long owner;
    /**
     * 未分配  跟进中 已转化为商机 放入线索池
     */
    private Integer state;
    private String createBy;
    private Date createTime;
    private String editBy;
    private Date editTime;
    private String description;
    /**
     * 最近一次跟进概述
     */
    private String contactSummary;
    /**
     * 下一次联系的时间
     */
    private Date nextContactTime;
}
