package com.heng.mkt.service;

import com.heng.mkt.domain.Activity;
import com.heng.base.service.IBaseService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jarvis-Smith
 * @since 2023-03-14
 */
public interface IActivityService extends IBaseService<Activity> {

    List<Activity> getActivitys(Long typeId);
}
