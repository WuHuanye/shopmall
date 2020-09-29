package com.example.demo.config;

import com.example.demo.interceptor.AdminUserInterceptor;
import com.example.demo.interceptor.UserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    UserInterceptor userInterceptor;
    @Autowired
    AdminUserInterceptor adminUserInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userInterceptor)    //用户权限拦截
                .addPathPatterns("/admin/**")
                .addPathPatterns("/mall/**")    //禁止mall下的所有请求
                .addPathPatterns("/user/sendInfo")  //没登录不能留言
                .addPathPatterns("/user/comment/**")  //没登录不能评论
//                .addPathPatterns("/user/**")
//                .excludePathPatterns("/mall/**")            //暂时放开有关交易的页面
                .excludePathPatterns("/")
                .excludePathPatterns("/mall/mall-goods-detail") //放行详情页面
                .excludePathPatterns("/mall/mall-product-detail/**")   //放行详情页面的js和css
                .excludePathPatterns("/mall/searchGoods")   //放行搜索页面
                .excludePathPatterns("/mall/searchGoods/**")   //放行搜索页面的js和css
                .excludePathPatterns("/user/quit"); //放行退出登录

        registry.addInterceptor(adminUserInterceptor)
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

    }
}
