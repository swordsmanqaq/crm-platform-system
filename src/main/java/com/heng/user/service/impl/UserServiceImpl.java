package com.heng.user.service.impl;

import com.heng.base.config.RegisterNoticeRabbitmqConfig;
import com.heng.base.constants.BaseConstants;
import com.heng.base.utils.MD5Utils;
import com.heng.user.domain.Logininfo;
import com.heng.user.domain.User;
import com.heng.user.dto.RegisterDTO;
import com.heng.user.dto.SendEmailAndMessageDTO;
import com.heng.user.mapper.LogininfoMapper;
import com.heng.user.mapper.UserMapper;
import com.heng.user.service.IUserService;
import com.heng.base.service.impl.BaseServiceImpl;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Jarvis-Smith
 * @since 2023-04-08
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements IUserService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LogininfoMapper logininfoMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 用户注册
     *
     * @param dto
     */
    @Override
    @Transactional
    public void registerSave(RegisterDTO dto) {
        //参数非空校验
        ParameterNonnullCheck(dto);

        //保存信息到logininfo
        Logininfo logininfo = new Logininfo();
        logininfo.setUsername(dto.getUsername());
        logininfo.setPhone(dto.getPhone());
        logininfo.setEmail(dto.getEmail());
        logininfo.setType(BaseConstants.LoginInfo.TYPE_USER);
        logininfo.setDisable(BaseConstants.LoginInfo.DISABLE_USE);
        //密码加密
        //生成随机字符串盐值
        String salt = UUID.randomUUID().toString();
        String md5Password = MD5Utils.encrypByMd5(salt + dto.getPassword() + "jarvis");
        logininfo.setSalt(salt);
        logininfo.setPassword(md5Password);
        logininfoMapper.insert(logininfo);

        //将信息存入User表
        User user = new User();
        BeanUtils.copyProperties(logininfo,user);
        user.setId(null);
        user.setState(BaseConstants.UserState.REGISTER_STATE);
        user.setCreatetime(new Date());
        user.setLogininfoId(logininfo.getId());
        userMapper.insert(user);

        //发送短信或者邮箱给用户
        //发短信
        SendEmailAndMessageDTO sendMessage = new SendEmailAndMessageDTO(dto.getPhone(),"恭喜你，注册成功，欢迎使用","");
        rabbitTemplate.convertAndSend(RegisterNoticeRabbitmqConfig.EXCHANGE_TOPICS_INFORM,"inform.sms",sendMessage);
        //发邮箱
        SendEmailAndMessageDTO sendEmail = new SendEmailAndMessageDTO(dto.getEmail(), "恭喜你，注册成功，欢迎使用", "注册成功");
        rabbitTemplate.convertAndSend(RegisterNoticeRabbitmqConfig.EXCHANGE_TOPICS_INFORM,"inform.email",sendEmail);
    }

    /**
     * 参数非空校验
     *
     * @param dto
     */
    public void ParameterNonnullCheck(RegisterDTO dto) {
        //所有参数的非空校验
        if (StringUtils.isEmpty(dto.getPhone()) || StringUtils.isEmpty(dto.getEmail()) || StringUtils.isEmpty(dto.getUsername()) || StringUtils.isEmpty(dto.getPassword()) || StringUtils.isEmpty(dto.getConfirmPassword()) || StringUtils.isEmpty(dto.getSmsCode())) {
            throw new RuntimeException("请填写所有信息");
        }
        //手机验证码比较
        //先判断是否过期
        if (Objects.isNull(redisTemplate.opsForValue().get(BaseConstants.MessageCode.REGISTER_SMS_CODE + dto.getPhone()))) {
            throw new RuntimeException("手机验证码已过期，请重新获取");
        }
        //没有过期在判断是否相等
        if (!dto.getSmsCode().equals(redisTemplate.opsForValue().get(BaseConstants.MessageCode.REGISTER_SMS_CODE + dto.getPhone()))) {
            throw new RuntimeException("手机验证码错误，请重新输入");
        }
        //用户名查重
        User user = userMapper.loadByUsername(dto.getUsername());
        if (Objects.nonNull(user)) {
            throw new RuntimeException("用户名已存在，请重新输入");
        }
        //两次密码验证
        if (!dto.getPassword().trim().equals(dto.getConfirmPassword().trim())) {
            throw new RuntimeException("两次密码不一致，请重新输入");
        }
    }
}
