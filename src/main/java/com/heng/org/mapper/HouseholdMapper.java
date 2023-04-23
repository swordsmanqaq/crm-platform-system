package com.heng.org.mapper;

import com.heng.org.domain.Household;
import com.heng.base.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    List<Long> getRoleByHouseholdId(Long householdId);

    void removeRole(Long householdId);

    void saveRole(@Param("householdId") Long householdId, @Param("roleId") List<Long> roleId);
}
