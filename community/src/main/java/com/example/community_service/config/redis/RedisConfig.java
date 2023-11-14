package com.example.community_service.config.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories
@EnableCaching
public class RedisConfig {

    // localhost(호스트 설정)
    @Value("${spring.data.redis.host}")
    private String host;

    // 포트 설정
    @Value("${spring.data.redis.port}")
    private int port;

    // Lettuce Connetion을 이용하여 Redis 서버와 통신
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(host, port);
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate() {

        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();

        // String 값 읽어서 저장. VO나 DTO같은 타입은 cast 불가능
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());

        // 모든 클래스타입 객체 json 형태로 저장. 가장 범용적인 방법
        GenericJackson2JsonRedisSerializer jackson2JsonRedisSerializer =
                new GenericJackson2JsonRedisSerializer();

        // ConnectionFactory 설정
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        return redisTemplate;
    }
}
