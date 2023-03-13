package com.heng.auth.dto;/**
 * @author shkstart
 * @create 2023-03-06 20:04
 */

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 *@Auther:Jarvis
 *@Date:2023年03月2023/3/6日20:04
 *@Description:
 */
@Data
public class RolePermissionDTO {

    private Long roleId;
    private List<String> permissionSns;
}
