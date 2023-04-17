package com.heng.org.domain;

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
 * @since 2023-03-25
 */
@Data
public class Household extends BaseDomain{

    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    private String password;
    private Integer age;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date checkTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date leaveTime;
    private String build;
}
