package top.openfbi.mdnote.config;

import jakarta.annotation.Resource;
import jakarta.servlet.MultipartConfigElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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

    @Value(value = "${File.OneFileSize}")
    private int ONE_FILE_SIZE;
    @Value(value = "${File.TotalFileSize}")
    private int TOTAL_FILE_SIZE;
    @Value(value = "${Session.CookieMaxAge}")
    private int COOKIE_MAX_AGE;
    private static final Logger logger
            = LoggerFactory.getLogger(WebConfig.class);



//    跨域问题
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

//    /**
//     *  跨域拦截器
//     */
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/**")
//                .addResourceLocations("classpath:static/images/");
//    }

    /**
     * 文件上传配置
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //文件最大
        factory.setMaxFileSize(DataSize.ofMegabytes(ONE_FILE_SIZE)); //KB,MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize(DataSize.ofMegabytes(TOTAL_FILE_SIZE));
        return factory.createMultipartConfig();
    }

    /**
     * 用户登录拦截
     */
    @Resource
    private LoginIntercept loginIntercept;
    @Resource
    private LnternalApiIntercept lnternalApiIntercept;
    /**
     *拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LoginIntercept());//可以直接new 也可以属性注入
        logger.debug("拦截器");

        //intercept拦截器
        registry.addInterceptor(lnternalApiIntercept).addPathPatterns("/api/internal/**");

        //登录拦截器
        registry.addInterceptor(loginIntercept).
                // 放行指定URL
                excludePathPatterns("/api/passport/login").
                excludePathPatterns("/api/passport/register").
                addPathPatterns("/**");    // 拦截其余 url
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
     * 设置客户端Cookie 生命周期为一个月
     */
    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        // 设置cookie名称
        serializer.setCookieName("session_id");
        //路径
        serializer.setCookiePath("/");
        //有效时间
        serializer.setCookieMaxAge(COOKIE_MAX_AGE);
        // 设置仅https协议使用此cookie
        serializer.setUseSecureCookie(true);
        // 设置cookie无法被js获取
        serializer.setUseHttpOnlyCookie(true);
        return serializer;
    }
}


