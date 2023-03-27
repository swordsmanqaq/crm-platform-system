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
public class EmployeeShop extends BaseDomain{

    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 关联employee_id
     */
    private Long employeeId;
    /**
     * 关联shop表的id
     */
    private Long shopId;
    /**
     * 1=店长 0=普通员工
     */
    private Integer isManager;
}
