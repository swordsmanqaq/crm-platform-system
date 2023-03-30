package com.heng.car.mapper;

import com.heng.car.domain.CarType;
import com.heng.base.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Jarvis-Smith
 * @since 2023-03-29
 */
public interface CarTypeMapper extends BaseMapper<CarType> {

    List<CarType> loadByPid(Long pid);
}
