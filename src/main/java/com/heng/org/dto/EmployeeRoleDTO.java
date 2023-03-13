package com.heng.org.dto;/**
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
public class EmployeeRoleDTO {
    private Integer employeeId;
    private List<Integer> roleId;
}
