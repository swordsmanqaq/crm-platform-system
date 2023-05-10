package com.heng.org.service.impl;

import com.heng.org.domain.Iccard;
import com.heng.org.mapper.IccardMapper;
import com.heng.org.service.IIccardService;
import com.heng.base.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jarvis-Smith
 * @since 2023-04-19
 */
@Service
public class IccardServiceImpl extends BaseServiceImpl<Iccard> implements IIccardService {

    @Autowired
    private IccardMapper iccardMapper;

    @Override
    @Transactional
    public void saveICLoss(Long userId) {
        Iccard iccard = iccardMapper.loadByUserId(userId);
        iccard.setUserId(userId);
        iccard.setState(-1L);
        iccardMapper.update(iccard);
    }

    //分配IC卡
    @Override
    @Transactional
    public void allocationIC(Iccard iccard) {
        Iccard IC = iccardMapper.loadById(iccard.getId());
        IC.setUserId(iccard.getHousehold().getId());
        IC.setState(1L);
        iccardMapper.update(IC);
    }
}
