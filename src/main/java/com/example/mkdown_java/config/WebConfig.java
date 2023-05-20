package com.example.mkdown_java.config;

import jakarta.servlet.MultipartConfigElement;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    //    @Override
    public void addCorsMappings(CorsRegistry registry) {
        CorsRegistration registration =   registry.addMapping("/**");
//        registration.allowedOrigins("http://127.0.0.1:8080");
        registration.allowedOrigins("http://localhost:8080");

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:static/images/");
    }

    /**
     * 文件上传配置
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //文件最大
        factory.setMaxFileSize(DataSize.ofMegabytes(10)); //KB,MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize(DataSize.ofMegabytes(30));
        return factory.createMultipartConfig();
    }

}


