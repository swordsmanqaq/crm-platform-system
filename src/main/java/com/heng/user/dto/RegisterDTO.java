package com.heng.user.dto;/**
 * @author shkstart
 * @create 2023-04-08 15:49
 */

import lombok.Data;

/**
 *@Auther:Jarvis
 *@Date:2023年04月2023/4/8日15:49
 *@Description:
 */
@Data
public class RegisterDTO {
    private String phone;
    private String smsCode;
    private String username;
    private String email;
    private String password;
    private String confirmPassword;
}
