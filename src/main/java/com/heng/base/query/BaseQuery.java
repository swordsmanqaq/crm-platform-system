package com.heng.base.query;/**
 * @author shkstart
 * @create 2023-03-02 11:10
 */

import lombok.Data;

/**
 *@Auther:Jarvis
 *@Date:2023年03月2023/3/2日11:10
 *@Description:
 */
@Data
public class BaseQuery {

    private Long currentPage;    //当前页码
    private Long pageSize;      //每页展示的数据条数
    private String keyword;       //高级搜索关键字

    public Long getStart() {
        return (currentPage - 1) * pageSize;
    }

}
