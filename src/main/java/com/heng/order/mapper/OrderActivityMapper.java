package com.heng.order.mapper;

import com.heng.order.domain.OrderActivity;
import com.heng.base.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Jarvis-Smith
 * @since 2023-03-16
 */
public interface OrderActivityMapper extends BaseMapper<OrderActivity> {

    void batchInsert(@Param("orderId") Long orderId, @Param("activityIds") List<Long> activityIds);
}
