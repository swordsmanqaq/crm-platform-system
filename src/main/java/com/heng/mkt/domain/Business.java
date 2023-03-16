package com.heng.mkt.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.heng.base.domain.BaseDomain;
import com.heng.prod.domain.Product;
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
public class Business extends BaseDomain{

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private Long clueId;
    private Clue clue;
    /**
     * 关联商品表t_prod_product表的主键
     */
    private Long productId;
    private Product product;
    /**
     * 产生商机那一刻记录的商品名字
     */
    private String productName;
    /**
     * 产生商机那一刻记录的商品价格
     */
    private BigDecimal productPrice;
    /**
     * 0 跟进中 1 缴纳定金 2 成单 -1 商机池
     */
    private Integer state;
}
