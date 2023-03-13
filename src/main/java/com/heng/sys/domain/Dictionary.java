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
public class Dictionary extends BaseDomain{

    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 标识
     */
    private String sn;
    /**
     * 标题
     */
    private String title;
    /**
     * 描述
     */
    private String intro;
    private Integer status;
}
