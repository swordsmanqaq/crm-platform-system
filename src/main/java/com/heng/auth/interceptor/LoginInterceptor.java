package com.heng.auth.interceptor;/**
 * @author shkstart
 * @create 2023-03-07 19:12
 */

import com.heng.auth.annotation.MyPermission;
import com.heng.auth.mapper.PermissionMapper;
import com.heng.base.utils.BaseMap;
import com.heng.org.domain.Household;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

/**
 *@Auther:Jarvis
 *@Date:2023年03月2023/3/7日19:12
 *@Description:
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private PermissionMapper permissionMapper;

    //前置拦截
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取前端传来的token
        String headerToken = request.getHeader("token");
        //判断token是否为空，如果是空，说明没登录过，需要拦截
        if (StringUtils.isEmpty(headerToken)){
            //返回jsan对象给前端，以便提示用户原因
            response.getWriter().write("{\"success\": false, \"message\": \"NoLogin\"}");
            return false;
        }
        //根据token查询用户信息，看是否为空，空则拦截
        Object loginUser = BaseMap.map.get(headerToken);

        if (StringUtils.isEmpty(loginUser)){
            response.getWriter().write("{\"success\": false, \"message\": \"NoLogin\"}");
            return false;
        }

        Household household = (Household) loginUser;

        //2、权限拦截
        if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            //获取当前方法
            Method method = handlerMethod.getMethod();
            //获取方法是否有自定义注解
            MyPermission annotation = method.getAnnotation(MyPermission.class);
            if (Objects.isNull(annotation)){
                return true;
            }
            //方法上有注解说明需要权限
            //获取当前用户的所有权限
            List<String> sns = permissionMapper.getAllPermissionByEmployeeId(household.getId());
            //拼接当前权限
            String sn = method.getDeclaringClass().getSimpleName() + ":" + method.getName();
            //判断用户是否有权限
            if (!sns.contains(sn)){
                //没有权限，返回前端错误信息
                response.getWriter().write("{\"success\": false, \"message\": \"NoPermission\"}");
                return false;
            }

        }
        //放行
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
