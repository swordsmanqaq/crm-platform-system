package com.heng.user.controller;/**
 * @author shkstart
 * @create 2023-04-06 17:38
 */

import com.heng.base.utils.AjaxResult;
import com.heng.user.dto.BindParamDTO;
import com.heng.user.dto.MessageCodeDTO;
import com.heng.user.service.IVerificationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther:Jarvis
 * @Date:2023年04月2023/4/6日17:38
 * @Description:
 */
@RestController
@RequestMapping("/verification")
public class VerificationCodeController {

    @Autowired
    private IVerificationCodeService iVerificationCodeService;

    /**
     * 获取图片验证码
     * @param imageCodeKey
     * @return
     */
    @GetMapping("/image/{imageCodeKey}")
    public AjaxResult getImg(@PathVariable("imageCodeKey") String imageCodeKey) {
        try {
            String imgBase = iVerificationCodeService.getImg(imageCodeKey);
            return AjaxResult.me().setResultObj(imgBase);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("获取图片验证码失败" + e.getMessage());
        }
    }

    /**
     * 注册发送手机验证码
     * @return
     */
    @PostMapping("/sms/register")
    public AjaxResult sendMessageCode(@RequestBody MessageCodeDTO dto){
        try {
            iVerificationCodeService.sendMessageCode(dto);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("发送手机验证码失败!"+e.getMessage());
        }
    }

    /**
     * 手机号登录发送验证码
     * @param dto
     * @return
     */
    @PostMapping("/sms/phone")
    public AjaxResult sendPhoneMessage(@RequestBody MessageCodeDTO dto){
        try {
            iVerificationCodeService.sendPhoneMessage(dto);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("发送手机验证码失败!"+e.getMessage());
        }
    }

    /**
     * 修改密码手机验证码
     * @param dto
     * @return
     */
    @PostMapping("/sms/changePassword")
    public AjaxResult sendPhoneMessageChangePassword(@RequestBody MessageCodeDTO dto){
        try {
            iVerificationCodeService.sendPhoneMessageChangePassword(dto);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("发送手机验证码失败!"+e.getMessage());
        }
    }


    /**
     * 绑定微信发送手机验证码接口
     * @param dto
     * @return
     */
    @PostMapping("/sms/bind")
    public AjaxResult sendPhoneMessageToBind(@RequestBody MessageCodeDTO dto){
        try {
            iVerificationCodeService.sendPhoneMessageToBind(dto);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("发送手机验证码失败!"+e.getMessage());
        }
    }

}
