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
public class Dictionaryitem extends BaseDomain{

    private static final long serialVersionUID = 1L;

    private Long id;
    private String title;
    private String value;
    private Integer sequence;
    private String intro;
    /**
     * 数据字典类型id
     */
    private Long parentId;
}
