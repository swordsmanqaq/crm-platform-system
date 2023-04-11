package com.heng.user.dto;/**
 * @author shkstart
 * @create 2023-04-11 18:27
 */

import lombok.Data;

/**
 *@Auther:Jarvis
 *@Date:2023年04月2023/4/11日18:27
 *@Description:
 */
@Data
public class BindParamDTO {
    private String phone;
    private String verifyCode;
    private String accessToken;
    private String openid;
}
