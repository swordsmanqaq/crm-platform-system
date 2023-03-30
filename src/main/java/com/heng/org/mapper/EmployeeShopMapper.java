package com.heng.org.mapper;

import com.heng.org.domain.EmployeeShop;
import com.heng.base.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Jarvis-Smith
 * @since 2023-03-27
 */
public interface EmployeeShopMapper extends BaseMapper<EmployeeShop> {

    EmployeeShop loadByShopId(@Param("id") Long id);

}
