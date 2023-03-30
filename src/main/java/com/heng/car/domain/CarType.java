package com.heng.car.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
public class CarType extends BaseDomain{

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String icon;
    private Integer index;
    private String description;
    /**
     * 父类型id
     */
    private Long pid;
    List<CarType> children;
}
