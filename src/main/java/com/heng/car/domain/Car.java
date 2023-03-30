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
    private Date createtime;
    private Date onsaletime;
    private Date offsaletime;
    /**
     * 状态：0下架 1上架
     */
    private Long state;
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
    private Long sellerId;
    /**
     * 如果被领养为领养用户id
     */
    private Long userId;
    /**
     * 寻主消息id
     */
    private Long searchMasterMsgId;
    /**
     * 审核状态
     */
    private Integer auditstate;
}
