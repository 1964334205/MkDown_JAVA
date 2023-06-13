package com.example.mkdown_java.config;

import jakarta.annotation.Resource;
import jakarta.servlet.MultipartConfigElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.util.unit.DataSize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableWebMvc
@SpringBootConfiguration
public class WebConfig implements WebMvcConfigurer {

    private static final Logger logger
            = LoggerFactory.getLogger(WebConfig.class);
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        System.out.println("addCorsMappings  生效");
//        registry.addMapping("/**")
//                .allowCredentials(true)
//                .allowedOrigins("http://localhost:8080")
//                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
//                .allowedHeaders("*")
//                .maxAge(3600);
////        registration.allowedOrigins("http://127.0.0.1:8080");
////        registration.allowedOrigins("http://localhost:8080");
//
//    }

    /**
     *  拦截器
     */
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

    @Resource
    private LoginIntercept loginIntercept;
    /**
     *拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LoginIntercept());//可以直接new 也可以属性注入
        logger.debug("拦截器");
        registry.addInterceptor(loginIntercept).
                excludePathPatterns("/api/User/login").
                excludePathPatterns("/api/User/register").
                addPathPatterns("/**");    // 拦截所有 url
    }

    /**
     * 统一请求头为api
     * 例：
     *      原URL：user/update
     *      修改后:api/user/update
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        logger.debug("configurePathMatch  生效");
        configurer.addPathPrefix("/api", c -> c.isAnnotationPresent(RequestMapping.class));
    }

    /**
     * 设置客户端Cookie 生命周期
     */
    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setCookieName("session_id");
        serializer.setCookiePath("/");
        serializer.setCookieMaxAge(30*24*60*60);
        return serializer;
    }
}


