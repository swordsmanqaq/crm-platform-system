package com.heng.user.domain;

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
 * @since 2023-04-08
 */
@Data
public class Logininfo extends BaseDomain{

    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    private String phone;
    private String email;
    private String salt;
    private String password;
    /**
     * 0 代表管理员 1 用户
     */
    private Integer type;
    /**
     * 0 不可以用 1 可用
     */
    private Integer disable;
}
