//package com.example.community_service.config.redis;
//
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import lombok.RequiredArgsConstructor;
//import org.springframework.cache.CacheManager;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.cache.RedisCacheConfiguration;
//import org.springframework.data.redis.cache.RedisCacheManager;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.RedisSerializationContext;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
//import java.time.Duration;
//
//@Configuration
//@RequiredArgsConstructor
//@EnableCaching
//public class CacheConfig {
//
//    private final RedisConnectionFactory redisConnectionFactory;
//
//    @Bean
//    public CacheManager redisCacheManager() {
//
//        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
//                .serializeKeysWith(
//                        RedisSerializationContext.SerializationPair.fromSerializer(
//                                new StringRedisSerializer()))
//                .serializeValuesWith(
//                        RedisSerializationContext.SerializationPair.fromSerializer(
//                                new GenericJackson2JsonRedisSerializer(objectMapper())))
//                .entryTtl(Duration.ofHours(1L)); // 캐시 만료 시간 1시간
//
//        RedisCacheManager redisCacheManager = RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(
//                redisConnectionFactory).cacheDefaults(redisCacheConfiguration).build();
//
//        return redisCacheManager;
//    }
//
//    @Bean
//    public ObjectMapper objectMapper() {
//
//        return new ObjectMapper()
//                .findAndRegisterModules()
//                .enable(SerializationFeature.INDENT_OUTPUT)
//                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
//                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
//                .registerModules(new JavaTimeModule());
//    }
//}
