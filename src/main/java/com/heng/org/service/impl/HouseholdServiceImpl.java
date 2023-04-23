package com.heng.org.service.impl;

import com.heng.org.domain.Household;
import com.heng.org.dto.HouseholdRoleDTO;
import com.heng.org.mapper.HouseholdMapper;
import com.heng.org.service.IHouseholdService;
import com.heng.base.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jarvis-Smith
 * @since 2023-03-25
 */
@Service
public class HouseholdServiceImpl extends BaseServiceImpl<Household> implements IHouseholdService {

    @Autowired
    private HouseholdMapper householdMapper;

    @Override
    public List<Long> getRoleByHouseholdId(Long householdId) {
        return householdMapper.getRoleByHouseholdId(householdId);
    }

    @Override
    @Transactional
    public void saveRole(HouseholdRoleDTO dto) {
        //先删除原本的，在添加新改的
        householdMapper.removeRole(dto.getHouseholdId());
        householdMapper.saveRole(dto.getHouseholdId(),dto.getRoleId());
    }
}
