package com.heng.auth.dto;/**
 * @author shkstart
 * @create 2023-03-08 15:29
 */

import lombok.Data;

import java.util.List;

/**
 *@Auther:Jarvis
 *@Date:2023年03月2023/3/8日15:29
 *@Description:
 */
@Data
public class RoleMenuDTO {
    private Integer roleId;
    private List<Integer> menuId;
}
