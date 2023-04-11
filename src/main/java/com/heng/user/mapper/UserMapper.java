package com.heng.user.mapper;

import com.heng.user.domain.User;
import com.heng.base.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Jarvis-Smith
 * @since 2023-04-08
 */
public interface UserMapper extends BaseMapper<User> {

    User loadByUsername(String username);

    User loadByPhone(String phone);

}
