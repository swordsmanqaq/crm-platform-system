package com.heng.mkt.mapper;

import com.heng.mkt.domain.Activity;
import com.heng.base.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Jarvis-Smith
 * @since 2023-03-14
 */
public interface ActivityMapper extends BaseMapper<Activity> {

    List<Activity> getActivitys(@Param("typeId") Long typeId);
}
