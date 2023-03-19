package com.heng.mkt.domain;

import java.math.BigDecimal;
import java.util.Date;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.heng.base.domain.BaseDomain;
import com.heng.org.domain.Employee;
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
public class Clue extends BaseDomain{

    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 全名
     */
    @Excel(name = "全称")
    private String fullName;

//    @Excel(name = "全称")
//    private String full_name;
    /**
     * 称呼
     */
    @Excel(name = "称呼")
    private String appellation;
    @Excel(name = "公司")
    private String company;
    @Excel(name = "职业")
    private String job;
    @Excel(name = "邮箱")
    private String email;
    @Excel(name = "手机")
    private String phone;
    @Excel(name = "移动电话")
    private String mphone;
    @Excel(name = "地址")
    private String address;
    /**
     * 客户来源，关联向数据字典明细
     */
    private Long source;
    private Dictionaryitem dictionaryitem;
    /**
     * 营销人员，关联向员工表
     */
    private Long owner;
    private Employee employee;
    /**
     * 未分配  跟进中 已转化为商机 放入线索池
     */
    private Integer state;
    private String createBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private String editBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date editTime;
    @Excel(name = "描述")
    private String description;
    /**
     * 最近一次跟进概述
     */
    private String contactSummary;
    /**
     * 下一次联系的时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date nextContactTime;
}
