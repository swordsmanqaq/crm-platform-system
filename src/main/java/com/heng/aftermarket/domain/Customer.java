package com.heng.aftermarket.domain;

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
public class Customer extends BaseDomain{

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private Integer age;
    private Integer gender;
    private String tel;
    private String email;
    private String qq;
    private String wechat;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date inputTime;
    private Integer state;
    private Long customersourceId;
    private Long jobId;
    private Long salarylevelId;
    private Long sellerId;
    private Long inputuserId;
}
