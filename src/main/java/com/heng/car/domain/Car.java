package com.heng.car.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.heng.base.domain.BaseDomain;
import com.heng.org.domain.Employee;
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
public class Car extends BaseDomain{

    private static final long serialVersionUID = 1L;

    private Long id;
    private String title;
    /**
     * 封面 fastdfs地址
     */
    private String cover;
    /**
     * 原价
     */
    private BigDecimal saleprice;
    /**
     * 售价
     */
    private BigDecimal costprice;
    private Integer isnew;
    /**
     * 上牌时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date reigstertime;
    /**
     * 里程
     */
    private Double mileage;
    /**
     * 店铺Id 如果被领养店铺id为null
     */
    private Long shopId;
    private String shopname;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createtime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date onsaletime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date offsaletime;
    /**
     * 状态：0下架 1上架
     */
    private Integer state;
    /**
     * 是否超值
     */
    private Integer costeffective;
    /**
     * 急售
     */
    private Integer rushsale;
    /**
     * 准新车
     */
    private Integer quasinewcar;
    private Integer transitivecountry;
    /**
     * 类型id
     */
    private Long typeId;
    private CarType type;


    private Long sellerId;
    private Employee seller;

    /**
     * 购买人的id
     */
    private Long userId;
    /**
     * 二手车发布消息的id
     */
    private Long searchMasterMsgId;
    /**
     * 审核状态   0-审核驳回 1-审核通过
     */
    private Integer auditstate;


    private CarDetail carDetail;

}
