package com.heng.mkt.service.impl;

import com.heng.mkt.domain.Activity;
import com.heng.mkt.mapper.ActivityMapper;
import com.heng.mkt.service.IActivityService;
import com.heng.base.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jarvis-Smith
 * @since 2023-03-14
 */
@Service
public class ActivityServiceImpl extends BaseServiceImpl<Activity> implements IActivityService {

    @Autowired
    private ActivityMapper activityMapper;
    @Override
    public List<Activity> getActivitys(Long typeId) {
        return activityMapper.getActivitys(typeId);
    }
}
