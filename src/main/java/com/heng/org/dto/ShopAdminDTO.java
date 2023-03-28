package com.heng.org.dto;/**
 * @author shkstart
 * @create 2023-03-28 11:01
 */

import lombok.Data;

/**
 *@Auther:Jarvis
 *@Date:2023年03月2023/3/28日11:01
 *@Description:
 */
@Data
public class ShopAdminDTO {
    private String username;
    private String password;
    private String phone;
    private String email;
    private String confirmPassword;
}
