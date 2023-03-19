package com.heng.mkt.dto;/**
 * @author shkstart
 * @create 2023-03-17 10:29
 */

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 *@Auther:Jarvis
 *@Date:2023年03月2023/3/17日10:29
 *@Description:
 */
@Data
public class LineChartDTO {
    @JsonFormat(pattern = "MM-dd")
    private Date date;
    private Long salesSum;
    private Long salesCount;
}
