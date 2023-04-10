package com.heng.user.mapper;

import com.heng.user.domain.Logininfo;
import com.heng.base.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Jarvis-Smith
 * @since 2023-04-08
 */
public interface LogininfoMapper extends BaseMapper<Logininfo> {

    Logininfo loadByUsernameAndType(@Param("username") String username, @Param("type") Integer type);

}
