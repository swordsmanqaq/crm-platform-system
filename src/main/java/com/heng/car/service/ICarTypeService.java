package com.heng.car.service;

import com.heng.car.domain.CarType;
import com.heng.base.service.IBaseService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jarvis-Smith
 * @since 2023-03-29
 */
public interface ICarTypeService extends IBaseService<CarType> {

    List<CarType> getTree(Long pid);
}
