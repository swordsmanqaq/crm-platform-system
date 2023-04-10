package com.heng.auth.controller;/**
 * @author shkstart
 * @create 2023-03-07 17:03
 */

import com.heng.auth.dto.LoginDTO;
import com.heng.auth.service.ILoginService;
import com.heng.base.utils.AjaxResult;
import com.heng.base.utils.BaseMap;
import com.heng.user.service.ILogininfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 *@Auther:Jarvis
 *@Date:2023年03月2023/3/7日17:03
 *@Description:
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private ILoginService loginService;

    @Autowired
    private ILogininfoService logininfoService;

    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 登录
     * @param dto
     * @return
     */
    @PostMapping("/map")
    public AjaxResult loginIn(@RequestBody LoginDTO dto){
        try {
            Map<String, Object> loginUser = loginService.loginIn(dto);
            return AjaxResult.me().setResultObj(loginUser);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage(e.getMessage());
        }
    }

    /**
     * 账号登录
     * @param dto
     * @return
     */
    @PostMapping("/account")
    public AjaxResult accountLoginIn(@RequestBody LoginDTO dto){
        try {
            Map<String, Object> loginUser = logininfoService.loginIn(dto);
            return AjaxResult.me().setResultObj(loginUser);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage(e.getMessage());
        }
    }

    /**
     * 手机号登录
     * @param dto
     * @return
     */
    @PostMapping("/phone")
    public AjaxResult phoneLoginIn(@RequestBody LoginDTO dto){
        try {
            Map<String, Object> loginUser = logininfoService.phoneLoginIn(dto);
            return AjaxResult.me().setResultObj(loginUser);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage(e.getMessage());
        }
    }


    @PostMapping("/changePassword")
    public AjaxResult changePasswordCommit(@RequestBody LoginDTO dto){
        try {
            logininfoService.changePasswordCommit(dto);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage(e.getMessage());
        }
    }

    /**
     * 登出
     * @param httpServletRequest
     * @return
     */
    @PostMapping("/logout")
    //获取请求头的信息
    public AjaxResult loginOut(HttpServletRequest httpServletRequest){
        try {
            String token = httpServletRequest.getHeader("token");
//            BaseMap.map.remove(token);
            redisTemplate.delete(token);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("退出登录失败" + e.getMessage());
        }
    }

}
