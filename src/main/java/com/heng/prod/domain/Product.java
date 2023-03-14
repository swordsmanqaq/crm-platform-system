package com.heng.prod.domain;

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
public class Product extends BaseDomain{

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private BigDecimal price;
    private String description;
    /**
     * 图片、视频地址
     */
    private String resource;
    private Long typeId;
    private ProductType type;

}
