package com.example.demo.Config;


import com.example.demo.model.data.MenuInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    private final MenuInterceptor menuInterceptor;

    public WebConfig(MenuInterceptor menuInterceptor) {
        this.menuInterceptor = menuInterceptor;
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Đăng ký interceptor và chỉ định các đường link mà nó áp dụng
        registry.addInterceptor(menuInterceptor).addPathPatterns("/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/**")
                .addResourceLocations("classpath:/static/"); // Thời gian cache 1 năm
    }


//@Override
//public void addResourceHandlers(final ResourceHandlerRegistry registry) {
//    registry.addResourceHandler("/upload/**").addResourceLocations("file://" + System.getProperty("user.dir") + "/src/main/upload/");
//}
}