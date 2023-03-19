package com.heng.order.mapper;

import com.heng.order.domain.Contract;
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
public interface ContractMapper extends BaseMapper<Contract> {

    Contract loadByOrderId(@Param("id") Long id);
}
