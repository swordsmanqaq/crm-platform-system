package com.heng.org.domain;

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
 * @since 2023-03-27
 */
@Data
public class Shop extends BaseDomain{

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String tel;
    private Date registerTime;
    /**
     * 1=待审核 2=审核通过待激活 3=激活成功 4=驳回
     */
    private Integer state;
    private String address;
    private String logo;
}
