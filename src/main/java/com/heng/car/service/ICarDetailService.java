package com.heng.car.service;

import com.heng.car.domain.CarDetail;
import com.heng.base.service.IBaseService;
import com.heng.org.domain.Employee;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jarvis-Smith
 * @since 2023-03-29
 */
public interface ICarDetailService extends IBaseService<CarDetail> {

    void saveCarReport(CarDetail carDetail, Employee loginUser);

    CarDetail loadByCarId(Long carId);
}
