package com.heng.org.domain;

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
 * @since 2023-04-15
 */
@Data
public class PersonEntryAndExitRecords extends BaseDomain{

    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 进出人员名字
     */
    private String name;
    /**
     * 进入时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date entryTime;
    /**
     * 信息
     */
    private String info;
    /**
     * 离开时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date leaveTime;

}
