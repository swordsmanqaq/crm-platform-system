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
public class Wechatuser extends BaseDomain{

    private static final long serialVersionUID = 1L;

    private Long id;
    private String openid;
    private String nickname;
    private Integer sex;
    private String address;
    private String headimgurl;
    private String unionid;
    private Long userId;
}
