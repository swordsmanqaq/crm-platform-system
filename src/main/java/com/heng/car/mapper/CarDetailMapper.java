package com.heng.car.mapper;

import com.heng.car.domain.CarDetail;
import com.heng.base.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Jarvis-Smith
 * @since 2023-03-29
 */
public interface CarDetailMapper extends BaseMapper<CarDetail> {

    CarDetail loadByCarId(@Param("carId") Long carId);


    void updateByCarId(CarDetail carDetail);
}
