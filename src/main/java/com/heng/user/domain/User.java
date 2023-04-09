package com.heng.user.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class User extends BaseDomain{

    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    private String email;
    private String phone;
    /**
     * 盐值
     */
    private String salt;
    /**
     * 密码，md5加密加盐
     */
    private String password;
    /**
     * 已注册 已激活 禁用
     */
    private Integer state;
    private Integer age;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createtime;
    private String headImg;
    private Long logininfoId;
}
