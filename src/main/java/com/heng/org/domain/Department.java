package com.heng.org.domain;/**
 * @author shkstart
 * @create 2023-03-01 14:31
 */

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 *@Auther:Jarvis
 *@Date:2023年03月2023/3/1日14:31
 *@Description:
 */
@Data
public class Department {
    @ApiParam(name = "主键")
    private Integer id;
    @ApiParam(name = "部门名称")
    private String name;
    @ApiParam(name = "部门介绍")
    private String intro;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date create_time;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date update_time;
    private Employee employee;
    private Department department;
    private List<Department> children;  //子部门
    @ApiParam(name = "路径")
    private String path;
    @ApiParam(name = "状态")
    private Integer state;

}
