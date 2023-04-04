package com.heng.car.mapper;

import com.heng.car.domain.Car;
import com.heng.base.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Jarvis-Smith
 * @since 2023-03-29
 */
public interface CarMapper extends BaseMapper<Car> {

    List<Car> loadByIds(@Param("ids")List<Long> ids, @Param("state")int state, @Param("auditState")int auditState);

    void updateState(@Param("carList")List<Long> carList, @Param("state")int state);
}
