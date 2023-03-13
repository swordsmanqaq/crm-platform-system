package com.heng.auth.domain;/**
 * @author shkstart
 * @create 2023-03-05 16:29
 */

import com.heng.base.domain.BaseDomain;
import lombok.Data;

import java.util.List;

/**
 *@Auther:Jarvis
 *@Date:2023年03月2023/3/5日16:29
 *@Description:
 */
@Data
public class Permission {

    private Integer id;
    private String name;
    private String url;
    private String descs;
    private String sn;
    private Integer parent_id;  // 父权限ID
    private Permission parent;  // 父权限 最主要作用做分类管理 一个权限只有一个父权限
    private List<Permission> children;

}
