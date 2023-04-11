package com.heng.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.heng.base.config.RegisterNoticeRabbitmqConfig;
import com.heng.base.constants.BaseConstants;
import com.heng.base.constants.WxConstants;
import com.heng.base.utils.HttpUtil;
import com.heng.base.utils.MD5Utils;
import com.heng.user.domain.Logininfo;
import com.heng.user.domain.User;
import com.heng.user.domain.Wechatuser;
import com.heng.user.dto.BindParamDTO;
import com.heng.user.dto.RegisterDTO;
import com.heng.user.dto.SendEmailAndMessageDTO;
import com.heng.user.mapper.LogininfoMapper;
import com.heng.user.mapper.UserMapper;
import com.heng.user.mapper.WechatuserMapper;
import com.heng.user.service.ILogininfoService;
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
import java.util.Map;
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

    @Autowired
    private WechatuserMapper wechatuserMapper;

    @Autowired
    private ILogininfoService iLogininfoService;

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
     * 绑定微信
     * @param dto
     * @return
     */
    @Override
    public Map<String, Object> bind(BindParamDTO dto) {
        //参数非空校验
        if (StringUtils.isEmpty(dto.getPhone()) || StringUtils.isEmpty(dto.getVerifyCode())){
            throw new RuntimeException("请填写所有信息");
        }
        if(StringUtils.isEmpty(dto.getAccessToken()) || StringUtils.isEmpty(dto.getOpenid())){
            throw new RuntimeException("网络错误，请刷新重新尝试");
        }
        //验证码校验
        if (StringUtils.isEmpty(redisTemplate.opsForValue().get(BaseConstants.MessageCode.BINDWECHAT_SMS_CODE + dto.getPhone()))){
            throw new RuntimeException("手机验证码已过期，请重新获取");
        }
        if (!dto.getVerifyCode().equals(redisTemplate.opsForValue().get(BaseConstants.MessageCode.BINDWECHAT_SMS_CODE + dto.getPhone()))){
            throw new RuntimeException("手机验证码错误，请重新输入");
        }
        //通过access_token 和 openid 调用微信接口获取用户信息
        String userUrl = WxConstants.GET_USER_URL.replace("ACCESS_TOKEN", dto.getAccessToken())
                                                 .replace("OPENID", dto.getOpenid());
        String userString = HttpUtil.httpGet(userUrl);
        //将userString字符串转换为json对象
        JSONObject jsonObject = JSONObject.parseObject(userString);
        //将userString字符串转换成Wechatuser对象
        Wechatuser wechatuser = JSONObject.parseObject(userString, Wechatuser.class);
        //设置Wechatuser对象中的address属性
        wechatuser.setAddress(jsonObject.getString("country") + jsonObject.getString("province") + jsonObject.getString("city"));

        //通过手机号查询用户是否存在于user表中
        User user = userMapper.loadByPhone(dto.getPhone());
        Logininfo logininfo = null;
        if (Objects.nonNull(user)){
            //存在则判断当前输入的手机号是否已经绑定过微信
            Wechatuser wxuser = wechatuserMapper.loadByUserId(user.getId());
            if (Objects.nonNull(wxuser)){
                //如果已经绑定过微信，则返回错误信息
                throw new RuntimeException("该手机号已绑定过微信,请直接登录");
            }
            //不存在则存入信息到微信用户表
            wechatuser.setUserId(user.getId());
            //查询logininfo表做免密登录
            logininfo = logininfoMapper.loadById(user.getLogininfoId());

        } else {
            //不存在 需要在三张表中创建信息，并且做好关联关系
            logininfo = addInformationForLoginInfo(dto.getPhone());
            logininfoMapper.insert(logininfo);
            //添加user信息
            User user1 = new User();
            BeanUtils.copyProperties(logininfo,user1);
            user1.setId(null);
            user1.setState(BaseConstants.UserState.REGISTER_STATE);
            user1.setCreatetime(new Date());
            user1.setLogininfoId(logininfo.getId());
            userMapper.insert(user1);
            //将用户信息和微信绑定
            wechatuser.setUserId(user1.getId());
        }
        //添加微信表信息
        wechatuserMapper.insert(wechatuser);
        //免密登录
        Map<String, Object> map = iLogininfoService.loginSuccess(logininfo);
        return map;

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

    /**
     * 添加logininfo对象方法
     * @param phone
     * @return
     */
    private Logininfo addInformationForLoginInfo(String phone) {
        Logininfo logininfo = new Logininfo();
        //随机生成一个username
        logininfo.setUsername("Jarvis" + UUID.randomUUID().toString());
        logininfo.setPhone(phone);
        //盐值
        String salt = UUID.randomUUID().toString();
        logininfo.setSalt(salt);
        String md5Password = MD5Utils.encrypByMd5(salt + "123456" + "jarvis");
        logininfo.setPassword(md5Password);
        logininfo.setType(BaseConstants.LoginInfo.TYPE_USER);
        logininfo.setDisable(BaseConstants.LoginInfo.DISABLE_USE);
        return logininfo;
    }
}
