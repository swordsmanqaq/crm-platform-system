package com.heng.user.service;

import com.heng.user.domain.User;
import com.heng.base.service.IBaseService;
import com.heng.user.dto.RegisterDTO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jarvis-Smith
 * @since 2023-04-08
 */
public interface IUserService extends IBaseService<User> {

    void registerSave(RegisterDTO dto);

}
