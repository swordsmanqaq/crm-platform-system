package com.heng.user.controller;/**
 * @author shkstart
 * @create 2023-04-06 17:38
 */

import com.heng.base.utils.AjaxResult;
import com.heng.user.service.IVerificationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
