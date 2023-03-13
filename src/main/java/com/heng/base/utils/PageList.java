package com.heng.base.utils;/**
 * @author shkstart
 * @create 2023-03-02 11:14
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 *@Auther:Jarvis
 *@Date:2023年03月2023/3/2日11:14
 *@Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageList<T> {

    private long total = 0;        //总条数
    private List<T>  rows = new ArrayList<T>();  //展示当前页的数据


}
