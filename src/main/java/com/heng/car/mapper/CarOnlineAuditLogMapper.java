package com.heng.car.mapper;

import com.heng.car.domain.CarOnlineAuditLog;
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
public interface CarOnlineAuditLogMapper extends BaseMapper<CarOnlineAuditLog> {

    void patchInsert(List<CarOnlineAuditLog> list);

}
