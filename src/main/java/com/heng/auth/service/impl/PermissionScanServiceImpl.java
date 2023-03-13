package com.heng.auth.service.impl;
/**
 * @author shkstart
 * @create 2023-03-05 15:56
 */

import com.heng.auth.annotation.MyPermission;
import com.heng.auth.domain.Permission;
import com.heng.auth.mapper.PermissionMapper;
import com.heng.auth.service.IPermissionScanService;
import com.heng.base.utils.ClassUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *@Auther:Jarvis
 *@Date:2023年03月2023/3/5日15:56
 *@Description:
 */
@Service
public class PermissionScanServiceImpl implements IPermissionScanService {

    @Value("${permission.scan-base-package}")
    private String scanBasePackage;

    @Autowired
    private PermissionMapper permissionMapper;

    @Transactional
    @Override
    public void scan() {
        System.out.println(this.scanBasePackage);
        //扫描打了自定义注解的类和方法
        //1.获得特定包下所有类的Class字解码文件
        List<Class> classes = ClassUtils.getClassName(this.scanBasePackage);
        List<Permission> permissions = new ArrayList<>();
        //先判断classes里面是否有文件，在执行循环获取
        if (classes != null && classes.size() > 0){
            //2.循环拿到所有的字节码文件
            for (Class clazz : classes) {
                //判断类(字节码文件)上是否有打了自定义注解
                MyPermission myPermission = (MyPermission) clazz.getAnnotation(MyPermission.class);
                if (Objects.isNull(myPermission)){
                    continue;
                }
                //获取上面url的路径
                RequestMapping requestMapping = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
                String clazzURL = requestMapping.value()[0];

                // 将类上面的@MyPermission注解解析为一个Permission对象
                Permission parent = new Permission();
                parent.setName(myPermission.name());
                parent.setDescs(myPermission.desc());
                parent.setUrl(clazzURL);
                parent.setSn(clazz.getSimpleName());
                permissions.add(parent);

                // 类上有注解，获取类里面所有的方法
                Method[] methods = clazz.getMethods();
                //3.循环所有的方法，找打了自定义注解的方法
                for (Method method : methods) {
                    MyPermission methodAnnotation = method.getAnnotation(MyPermission.class);
                    if (Objects.isNull(methodAnnotation)){
                        continue;
                    }
                    //打了自定义注解的方法，获取name、desc、拼接sn url
                    Permission methodPermission = new Permission();
                    methodPermission.setName(methodAnnotation.name());
                    methodPermission.setDescs(methodAnnotation.desc());
                    String sn = clazz.getSimpleName() + ":" + method.getName();
                    methodPermission.setSn(sn);
                    //url指的是前端在访问时需要访问的url,
                    //它是类上面的@RequestMapping的值+方法上面的url
                    String methodURL = getMethodUrl(method);
                    methodPermission.setUrl(clazzURL + methodURL);
                    methodPermission.setParent(parent);
                    permissions.add(methodPermission);
                }
            }
        }
        //4.将Permission对象存到数据库
        for (Permission permission : permissions) {
            permissionMapper.insert(permission);
        }

    }



    private String getMethodUrl(Method method) {
        String methodUrl = "";
        PutMapping putMapping = method.getAnnotation(PutMapping.class);
        if(Objects.nonNull(putMapping)){
            methodUrl = putMapping.value() != null && putMapping.value().length > 0 ? putMapping.value()[0] : "";
        }
        PostMapping postMapping = method.getAnnotation(PostMapping.class);
        if(Objects.nonNull(postMapping)){
            methodUrl = postMapping.value() != null && postMapping.value().length > 0 ? postMapping.value()[0] : "";
        }
        GetMapping getMapping = method.getAnnotation(GetMapping.class);
        if(Objects.nonNull(getMapping)){
            methodUrl = getMapping.value() != null && getMapping.value().length > 0? getMapping.value()[0] : "";
        }
        DeleteMapping deleteMapping = method.getAnnotation(DeleteMapping.class);
        if(Objects.nonNull(deleteMapping)){
            methodUrl = deleteMapping.value() != null && deleteMapping.value().length > 0 ? deleteMapping.value()[0] : "";
        }
        PatchMapping patchMapping = method.getAnnotation(PatchMapping.class);
        if(Objects.nonNull(patchMapping)){
            methodUrl = patchMapping.value() != null && patchMapping.value().length > 0 ? patchMapping.value()[0] : "";
        }
        return methodUrl;
    }
}
