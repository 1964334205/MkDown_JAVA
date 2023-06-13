package com.example.mkdown_java.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration()
/**
 * 配置springSession使用redis为session为数据库。
 * 配置session生命周期
 */
@EnableRedisHttpSession(redisNamespace = "userSession", maxInactiveIntervalInSeconds = 30*24*60*60)
public class RedisHttpSessionConfig {
    @Bean
    public RedisSerializer springSessionDefaultRedisSerializer() {
        return RedisSerializer.json();
    }

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory();
    }
}
