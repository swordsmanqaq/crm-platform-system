package com.heng.car.domain;

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
 * @since 2023-03-29
 */
@Data
public class CarDetail extends BaseDomain{

    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 基本信息
     */
    private String info;
    private Long carId;
    /**
     * 车辆标题
     */
    private String cartitle;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date detectiontime;

    private Long detectorId;
    /**
     * 检测员名称
     */
    private String detectorname;
    /**
     * 事故排查 图片地址
     */
    private String accidentInvestigation;
    /**
     * 核心部件检测
     */
    private String coreComponentsInvestigation;
    /**
     * 常用功能检测
     */
    private String commonFunctionsInvestigation;
    /**
     * 外观内饰检测
     */
    private String appearanceInspection;
}
