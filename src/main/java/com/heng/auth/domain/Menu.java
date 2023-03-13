package com.heng.auth.domain;/**
 * @author shkstart
 * @create 2023-03-08 09:18
 */

import com.heng.base.domain.BaseDomain;
import lombok.Data;

import java.util.List;

/**
 *@Auther:Jarvis
 *@Date:2023年03月2023/3/8日09:18
 *@Description:
 */
@Data
public class Menu {

    private Long id;
    private String name;
    private String url;
    private String icon;
    private Menu menu;
    private List<Menu> children;
}
