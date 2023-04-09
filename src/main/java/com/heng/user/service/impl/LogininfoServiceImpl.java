package com.heng.user.service.impl;

import com.heng.base.constants.BaseConstants;
import com.heng.base.utils.MD5Utils;
import com.heng.org.domain.Employee;
import com.heng.user.domain.Logininfo;
import com.heng.user.mapper.LogininfoMapper;
import com.heng.user.service.ILogininfoService;
import com.heng.base.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jarvis-Smith
 * @since 2023-04-08
 */
@Service
public class LogininfoServiceImpl extends BaseServiceImpl<Logininfo> implements ILogininfoService {

    @Autowired
    private LogininfoMapper logininfoMapper;

    @Override
    public void increase(Employee employee) {
        Logininfo logininfo = new Logininfo();
        logininfo.setUsername(employee.getUsername());
        logininfo.setPhone(employee.getPhone());
        logininfo.setEmail(employee.getEmail());
        logininfo.setType(BaseConstants.LoginInfo.TYPE_MANAGER);
        logininfo.setDisable(BaseConstants.LoginInfo.DISABLE_USE);
        //密码处理
        String salt = UUID.randomUUID().toString();
        String md5Password = MD5Utils.encrypByMd5(salt + employee.getPassword() + "jarvis");
        logininfo.setSalt(salt);
        logininfo.setPassword(md5Password);
        logininfoMapper.insert(logininfo);

        employee.setLogininfoId(logininfo.getId());
        employee.setSalt(salt);
        employee.setPassword(md5Password);
    }
}
