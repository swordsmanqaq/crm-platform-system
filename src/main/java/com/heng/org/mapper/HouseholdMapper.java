package com.heng.org.mapper;

import com.heng.org.domain.Household;
import com.heng.base.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Jarvis-Smith
 * @since 2023-03-25
 */
public interface HouseholdMapper extends BaseMapper<Household> {

    Household loadByUsername(String username);

}
