package com.heng.org.dto;/**
 * @author shkstart
 * @create 2023-04-18 20:25
 */

import lombok.Data;

import java.util.List;

/**
 *@Auther:Jarvis
 *@Date:2023年04月2023/4/18日20:25
 *@Description:
 */
@Data
public class HouseholdRoleDTO {

    private Long householdId;
    private List<Long> roleId;

}
