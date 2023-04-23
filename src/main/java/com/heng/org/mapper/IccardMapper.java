package com.heng.org.mapper;

import com.heng.org.domain.Iccard;
import com.heng.base.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Jarvis-Smith
 * @since 2023-04-19
 */
public interface IccardMapper extends BaseMapper<Iccard> {


    Iccard loadByUserId(Long userId);

}
