package com.heng.auth.service.impl;/**
 * @author shkstart
 * @create 2023-03-07 17:24
 */

import com.heng.auth.dto.LoginDTO;
import com.heng.auth.service.ILoginService;
import com.heng.base.utils.BaseMap;
import com.heng.org.domain.Employee;
import com.heng.org.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 *@Auther:Jarvis
 *@Date:2023年03月2023/3/7日17:24
 *@Description:
 */
@Service
@Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
public class LoginServiceImpl implements ILoginService {
    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public Map<String, Object> loginIn(LoginDTO dto) {
        //判断用户名是否为空
        if (StringUtils.isEmpty(dto.getUsername()) || StringUtils.isEmpty(dto.getPassword())){
            throw new RuntimeException("用户名或密码不能为空");
        }
        //根据用户名查询数据库的用户信息
        Employee employee = employeeMapper.loadByUsername(dto.getUsername());
        if (Objects.isNull(employee)){
            throw new RuntimeException("用户名或密码错误");
        }
        //校验用户名的密码是否正确
        if (!dto.getPassword().equals(employee.getPassword())){
            throw new RuntimeException("用户名或密码错误");
        }

//        登录成功，需要返回前端token和loginUser
        Map<String, Object> userMap = new HashMap<>();
        //通过UUID生成随即字符串作为key，用户信息作为value
        String token = UUID.randomUUID().toString();
        BaseMap.map.put(token, employee);
        //把密码制空
        employee.setPassword("");
        userMap.put("loginUser",employee);
        userMap.put("token", token);

        //返回map
        return userMap;
    }
}
