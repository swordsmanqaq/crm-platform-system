package com.heng.user.service;

import com.heng.org.domain.Employee;
import com.heng.user.domain.Logininfo;
import com.heng.base.service.IBaseService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jarvis-Smith
 * @since 2023-04-08
 */
public interface ILogininfoService extends IBaseService<Logininfo> {

    void increase(Employee employee);

}
