package com.heng.base.utils;/**
 * @author shkstart
 * @create 2023-03-15 09:40
 */

import com.heng.org.domain.Employee;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 *@Auther:Jarvis
 *@Date:2023年03月2023/3/15日09:40
 *@Description:登录上下文，获取当前登录人的信息，存入全局map
 */
public class LoginContext {

    /**
     * 存放登录信息
     */
    public static void setLoginUser(Employee loginUser){

    }

    /**
     * 获取登录信息
     */
    public static Employee getLoginUser(HttpServletRequest request){
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)){
            Employee employee = new Employee();
            employee.setId(1L);
            employee.setUsername("admin");
            return employee;
        }else {
            Object obj=BaseMap.map.get(token);
            if (Objects.isNull(obj)){
                Employee employee=new Employee();
                employee.setId(1L);
                employee.setUsername("admin");
                return employee;
            }
            return (Employee) obj;
        }

    }

}
