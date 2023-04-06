package com.heng.user.service.impl;/**
 * @author shkstart
 * @create 2023-04-06 18:45
 */

import com.heng.base.utils.VerifyCodeUtils;
import com.heng.user.service.IVerificationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 *@Auther:Jarvis
 *@Date:2023年04月2023/4/6日18:45
 *@Description:
 */
@Service
public class VerificationCodeServiceImpl implements IVerificationCodeService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${jarvis.verificationCodeExpiration.timeout}")
    private Integer codeTimeout;

    @Override
    public String getImg(String imageCodeKey) {
        try {
            //生成随机四位的验证码字符串
            String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
            //将uuid作为key 随机字符串作为value 存入redis中
            redisTemplate.opsForValue().set(imageCodeKey,verifyCode,this.codeTimeout, TimeUnit.MINUTES);
            //将字符串画到图片中
            String imgCode = VerifyCodeUtils.VerifyCode(120, 41, verifyCode);
            //返回base64格式的字符串给前端
            return imgCode;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
