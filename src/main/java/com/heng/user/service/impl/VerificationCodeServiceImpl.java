package com.heng.user.service.impl;/**
 * @author shkstart
 * @create 2023-04-06 18:45
 */

import com.heng.base.constants.BaseConstants;
import com.heng.base.utils.SmsUtil;
import com.heng.base.utils.VerifyCodeUtils;
import com.heng.user.dto.MessageCodeDTO;
import com.heng.user.service.IVerificationCodeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @Auther:Jarvis
 * @Date:2023年04月2023/4/6日18:45
 * @Description:
 */
@Service
public class VerificationCodeServiceImpl implements IVerificationCodeService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${jarvis.verificationCodeExpiration.timeout}")
    private Integer codeTimeout;

    @Value("${jarvis.messageCodeExpiration.timeout}")
    private Integer messageCodeTimeout;


    /**
     * 生成图片验证码
     *
     * @param imageCodeKey
     * @return
     */
    @Override
    public String getImg(String imageCodeKey) {
        try {
            //生成随机四位的验证码字符串
            String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
            //将uuid作为key 随机字符串作为value 存入redis中
            redisTemplate.opsForValue().set(imageCodeKey, verifyCode, this.codeTimeout, TimeUnit.MINUTES);
            //将字符串画到图片中
            String imgCode = VerifyCodeUtils.VerifyCode(120, 41, verifyCode);
            //返回base64格式的字符串给前端
            return imgCode;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 发送手机验证码
     *
     * @param dto
     */
    @Override
    public void sendMessageCode(MessageCodeDTO dto) {
        //参数的非空校验
        if (StringUtils.isEmpty(dto.getPhone()) || StringUtils.isEmpty(dto.getImageCode())) {
            throw new RuntimeException("手机号和图片验证码不能为空");
        }
        //判断图片验证码是否正确
        Object imageCode = redisTemplate.opsForValue().get(dto.getImageCodeKey());
        //判断是否存在
        if (Objects.isNull(imageCode)) {
            throw new RuntimeException("图片验证码已过期，请重新获取并输入");
        }
        //存在 判断是否正确
        if (!dto.getImageCode().equalsIgnoreCase(imageCode.toString())) {
            throw new RuntimeException("图片验证码不正确，请重新输入");
        }

        //给手机号发送验证码，并将验证码存入redis并设置过期时间
        //先判断手机号是否已经存在验证码
        String redisCodeKey = BaseConstants.MessageCode.REGISTER_SMS_CODE + dto.getPhone();
        Object valueResult = redisTemplate.opsForValue().get(redisCodeKey);
        String redisCodeValue = "";
        if (Objects.nonNull(valueResult)) {
            //存在验证码
            //判断是否已过重发时间，即一分钟
            Long expireTime = redisTemplate.opsForValue().getOperations().getExpire(redisCodeKey, TimeUnit.MINUTES);
            if (expireTime >= 4) {
                throw new RuntimeException("请不要频繁发送验证码");
            } else {
                //过了重发时间，将验证码重新设置过期时间放入redis
                redisCodeValue = valueResult.toString();
            }
        } else {
            //验证码不存在
            //随机生成一个六位数的验证码
            redisCodeValue = VerifyCodeUtils.generateVerifyMessageCode(6);
        }
        redisTemplate.opsForValue().set(redisCodeKey, redisCodeValue, this.messageCodeTimeout, TimeUnit.MINUTES);

        //将短信验证码发送给用户
        String content = String.format("您的注册验证码为%s，请不要随意泄漏，有效期为五分钟，请及时输入", redisCodeValue);
//        SmsUtil.sendSms(dto.getPhone(),content);
        System.out.println(content);
    }
}
