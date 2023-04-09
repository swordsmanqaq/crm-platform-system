package com.heng.user.dto;/**
 * @author shkstart
 * @create 2023-04-09 11:20
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *@Auther:Jarvis
 *@Date:2023年04月2023/4/9日11:20
 *@Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendEmailAndMessageDTO {
    private String addressee;
    private String content;
    private String theme;

}
