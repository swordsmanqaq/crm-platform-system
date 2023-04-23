package com.heng.org.service;

import com.heng.org.domain.Household;
import com.heng.base.service.IBaseService;
import com.heng.org.dto.HouseholdRoleDTO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jarvis-Smith
 * @since 2023-03-25
 */
public interface IHouseholdService extends IBaseService<Household> {

    List<Long> getRoleByHouseholdId(Long householdId);

    void saveRole(HouseholdRoleDTO dto);

}
