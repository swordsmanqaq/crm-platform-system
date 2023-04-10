package com.heng.user.service.impl;

import com.heng.auth.dto.LoginDTO;
import com.heng.base.constants.BaseConstants;
import com.heng.base.utils.MD5Utils;
import com.heng.org.domain.Employee;
import com.heng.user.domain.Logininfo;
import com.heng.user.mapper.LogininfoMapper;
import com.heng.user.service.ILogininfoService;
import com.heng.base.service.impl.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Jarvis-Smith
 * @since 2023-04-08
 */
@Service
public class LogininfoServiceImpl extends BaseServiceImpl<Logininfo> implements ILogininfoService {

    @Autowired
    private LogininfoMapper logininfoMapper;

    @Autowired
    private RedisTemplate redisTemplate;

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

    /**
     * 账号密码登录
     *
     * @param dto
     * @return
     */
    @Override
    public Map<String, Object> loginIn(LoginDTO dto) {
        //参数非空校验
        if (StringUtils.isEmpty(dto.getUsername()) || StringUtils.isEmpty(dto.getPassword()) || Objects.isNull(dto.getType())) {
            throw new RuntimeException("登录信息不能为空，请重新输入");
        }
        //根据用户名和类型查询logininfo对象
        Logininfo logininfo = logininfoMapper.loadByUsernameAndType(dto.getUsername(), dto.getType());
        if (Objects.isNull(logininfo)) {
            throw new RuntimeException("用户名或密码错误，请重新输入");
        }
        //对密码进行Md5加密处理后做比对
        String md5Password = MD5Utils.encrypByMd5(logininfo.getSalt() + dto.getPassword() + "jarvis");
        if (!md5Password.equals(logininfo.getPassword())) {
            throw new RuntimeException("用户名或密码错误，请重新输入");
        }

        //创建随机字符串作为token
        String token = UUID.randomUUID().toString();
        //将用户信息存入到redis中
        redisTemplate.opsForValue().set(token, logininfo, 30, TimeUnit.MINUTES);

        //将信息返回给前端
        Map<String, Object> map = new HashMap<>();
        //将敏感信息置空
        logininfo.setSalt("");
        logininfo.setPassword("");
        map.put("token", token);
        map.put("loginUser", logininfo);

        //返回信息
        return map;
    }

    /**
     * 手机号登录
     *
     * @param dto
     * @return
     */
    @Override
    public Map<String, Object> phoneLoginIn(LoginDTO dto) {
        //参数非空校验
        if (StringUtils.isEmpty(dto.getUsername())) {
            throw new RuntimeException("请填写所有信息");
        }
        //根据用户名和类型查询logininfo对象
        Logininfo logininfo = logininfoMapper.loadByUsernameAndType(dto.getUsername(), dto.getType());
        if (Objects.isNull(logininfo)) {
            throw new RuntimeException("手机号码错误，请重新输入");
        }
        //手机验证码比较
        //先判断是否过期
        if (Objects.isNull(redisTemplate.opsForValue().get(BaseConstants.MessageCode.LOGIN_SMS_CODE + dto.getUsername()))) {
            throw new RuntimeException("手机验证码已过期，请重新获取");
        }
        //没有过期在判断是否相等
        if (!dto.getSmsCode().equals(redisTemplate.opsForValue().get(BaseConstants.MessageCode.LOGIN_SMS_CODE + dto.getUsername()))) {
            throw new RuntimeException("手机验证码错误，请重新输入");
        }

        //创建随机字符串作为token
        String token = UUID.randomUUID().toString();
        //将用户信息存入到redis中
        redisTemplate.opsForValue().set(token, logininfo, 30, TimeUnit.MINUTES);

        //将信息返回给前端
        Map<String, Object> map = new HashMap<>();
        //将敏感信息置空
        logininfo.setSalt("");
        logininfo.setPassword("");
        map.put("token", token);
        map.put("loginUser", logininfo);

        //返回信息
        return map;
    }

    /**
     * 手机修改密码
     * @param dto
     * @return
     */
    @Override
    public void changePasswordCommit(LoginDTO dto) {
        //参数校验
        if (StringUtils.isEmpty(dto.getUsername()) || StringUtils.isEmpty(dto.getSmsCode()) || StringUtils.isEmpty(dto.getPassword()) || StringUtils.isEmpty(dto.getConfirmPassword())) {
            throw new RuntimeException("请填写所有信息");
        }
        //根据用户名和类型查询logininfo对象
        Logininfo logininfo = logininfoMapper.loadByUsernameAndType(dto.getUsername(), dto.getType());
        if (Objects.isNull(logininfo)){
            throw new RuntimeException("手机号码错误，请重新输入");
        }
        //验证码是否过期
        if (Objects.isNull(redisTemplate.opsForValue().get(BaseConstants.MessageCode.CHANGEPASSWORD_SMS_CODE + dto.getUsername()))){
            throw new RuntimeException("手机验证码已过期，请重新获取");
        }
        //判断验证码是否正确
        if (!dto.getSmsCode().equals(redisTemplate.opsForValue().get(BaseConstants.MessageCode.CHANGEPASSWORD_SMS_CODE + dto.getUsername()))){
            throw new RuntimeException("手机验证码错误，请重新输入");
        }

        String md5Password = MD5Utils.encrypByMd5(logininfo.getSalt() + dto.getPassword() + "jarvis");
        logininfo.setPassword(md5Password);
        logininfoMapper.update(logininfo);

    }

}
