package com.heng.finance.mapper;

import com.heng.finance.domain.Deposit;
import com.heng.base.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Jarvis-Smith
 * @since 2023-03-16
 */
public interface DepositMapper extends BaseMapper<Deposit> {

    Deposit getOrderDeposit(@Param("id") Long id);

}
