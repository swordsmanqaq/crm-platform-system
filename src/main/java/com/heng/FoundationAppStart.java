package com.heng;

import com.heng.auth.interceptor.LoginInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Hello world!
 */
@SpringBootApplication
@MapperScan("com.heng.*.mapper")     //用于扫描mapper包下的接口
@ServletComponentScan("com.heng.auth.listener")   //用于扫描监听器
public class FoundationAppStart implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    public static void main( String[] args ) {
        SpringApplication.run(FoundationAppStart.class,args);
    }

//    重写添加拦截器方法
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")   //拦截所有
                .excludePathPatterns("/login/**","/verification/**");  //放行资源
    }

}
