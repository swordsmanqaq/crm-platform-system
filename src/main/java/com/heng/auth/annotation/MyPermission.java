package com.heng.auth.annotation;

import org.apache.ibatis.annotations.Mapper;

import java.lang.annotation.*;

/**
 * @author shkstart
 * @create 2023-03-05 15:45
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapper
public @interface MyPermission {
    String name();       // 如果没有赋默认值的话,在使用注解时如果不给它赋值就会报错
    String desc() default "";
}
