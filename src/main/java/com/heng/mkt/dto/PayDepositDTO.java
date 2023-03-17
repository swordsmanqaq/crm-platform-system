package com.heng.mkt.dto;/**
 * @author shkstart
 * @create 2023-03-16 19:05
 */

import lombok.Data;

import java.util.List;

/**
 *@Auther:Jarvis
 *@Date:2023年03月2023/3/16日19:05
 *@Description:
 */
@Data
public class PayDepositDTO {
    private Long businessId;
    private Long deposit;
    private List<Long> activityIds;
}
