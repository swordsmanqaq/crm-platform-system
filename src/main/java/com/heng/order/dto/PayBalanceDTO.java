package com.heng.order.dto;/**
 * @author shkstart
 * @create 2023-03-17 22:04
 */

import com.heng.sys.domain.Dictionaryitem;
import lombok.Data;

/**
 *@Auther:Jarvis
 *@Date:2023年03月2023/3/17日22:04
 *@Description:
 */
@Data
public class PayBalanceDTO {
    private Long id;
    private Dictionaryitem payModel;
}
