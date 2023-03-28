package com.heng.org.dto;/**
 * @author shkstart
 * @create 2023-03-28 10:58
 */

import lombok.Data;

/**
 *@Auther:Jarvis
 *@Date:2023年03月2023/3/28日10:58
 *@Description:
 */
@Data
public class ShopRegisterDTO {
    private String name;
    private String tel;
    private String address;
    private String logo;
    private ShopAdminDTO admin;
}
