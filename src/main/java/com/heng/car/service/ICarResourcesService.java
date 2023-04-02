package com.heng.car.service;

import com.heng.car.domain.CarResources;
import com.heng.base.service.IBaseService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jarvis-Smith
 * @since 2023-03-29
 */
public interface ICarResourcesService extends IBaseService<CarResources> {

    void saveCarResource(CarResources carResources);

    CarResources loadByCarId(Long carId);
}
