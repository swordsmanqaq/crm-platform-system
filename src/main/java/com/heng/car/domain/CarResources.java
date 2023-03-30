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
public class CarResources extends BaseDomain{

    private static final long serialVersionUID = 1L;

    private Long id;
    private String viceimg;
    /**
     * 如果是多张图使用逗号分隔
     */
    private String img;
    /**
     * 跟多图
     */
    private String moreimg;
    private Long carId;
}
