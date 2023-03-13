package com.heng.sys.domain;

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
 * @since 2023-03-13
 */
@Data
public class Config extends BaseDomain{

    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 配置信息的key
     */
    private String key;
    /**
     * 配置信息的值
     */
    private String value;
    private Long type;
    /**
     * 简介
     */
    private String intro;
}
