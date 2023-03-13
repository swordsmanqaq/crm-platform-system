package com.heng.auth.listener;/**
 * @author shkstart
 * @create 2023-03-05 15:52
 */

import com.heng.auth.mapper.PermissionMapper;
import com.heng.auth.service.IPermissionScanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *@Auther:Jarvis
 *@Date:2023年03月2023/3/5日15:52
 *@Description:
 * 有个问题待考察：
 * 用@Component注解，有用@WebListener注解的，
 * 可能存在一个问题，部署到tomcat后service不能注入，为null。
 */
@Component
public class PermissionScanListener implements ServletContextListener {

    @Autowired
    private PermissionMapper mapper;

    @Autowired
    private IPermissionScanService permissionScanner;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("开始注解扫描");
        //先将数据库的权限删除,然后再添加
        mapper.removeAll();
        permissionScanner.scan();
        System.out.println("结束注解扫描");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("容器销毁-------------------------");
    }
}
