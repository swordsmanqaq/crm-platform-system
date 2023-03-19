package com.heng.mkt.dto;/**
 * @author shkstart
 * @create 2023-03-17 13:12
 */

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.heng.org.domain.Employee;
import com.heng.sys.domain.Dictionaryitem;
import lombok.Data;
import org.springframework.ui.Model;

import java.util.Date;

/**
 *@Auther:Jarvis
 *@Date:2023年03月2023/3/17日13:12
 *@Description:
 */
@Data
public class ImportDTO {
    @Excel(name = "全称")
    private String fullName;
    @Excel(name = "称呼")
    private String appellation;
    @Excel(name = "公司")
    private String company;
    @Excel(name = "职业")
    private String job;
    @Excel(name = "邮箱")
    private String email;
    @Excel(name = "手机")
    private String phone;
    @Excel(name = "移动电话")
    private String mphone;
    @Excel(name = "地址")
    private String address;
    @Excel(name = "描述")
    private String description;
}
