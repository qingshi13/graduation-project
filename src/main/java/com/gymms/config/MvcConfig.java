package com.gymms.config;

import com.gymms.util.LoginInterceptor;
import com.gymms.util.RefreshTokenInterceptor;
import com.gymms.util.UserHolder;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        // 登录拦截器
//        registry.addInterceptor(new LoginInterceptor())
//                .excludePathPatterns(
//                        "/voucher/**",
//                        "/shop-type/**",
//                        "/upload/**",
//                        "/blog/hot",
//                        "/code",
//                        "/login"
//                ).order(1);
//        // token刷新的拦截器
//        System.out.println("token刷新的拦截器");
//        registry.addInterceptor(new RefreshTokenInterceptor(stringRedisTemplate)).addPathPatterns("/**").order(0);
//    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //   访问相对路径
        registry.addResourceHandler("/avatar/**").addResourceLocations("classpath:/avatar/");
        registry.addResourceHandler("/good/**").addResourceLocations("classpath:/good/");
    }

}
