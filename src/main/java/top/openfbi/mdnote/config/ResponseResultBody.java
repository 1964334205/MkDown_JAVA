package top.openfbi.mdnote.config;

import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.annotation.*;

/**
 * 自定义注解
 * 作用：规范化返回值
 * 描述：配置了此注解的类和方法都会被com.example.mkdown_java.common.Result类包裹返回
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@ResponseBody
public @interface ResponseResultBody {

}

