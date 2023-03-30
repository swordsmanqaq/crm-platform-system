package com.heng.base.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;

//缓存的配置
@Configuration
public class RedisConfig {

    @Resource
    private RedisConnectionFactory factory;


    //使用JSON进行序列化
    @Bean
    public RedisTemplate<Object, Object> redisTemplate() {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        //JSON格式序列化
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
         //key的序列化
        redisTemplate.setKeySerializer(genericJackson2JsonRedisSerializer);
        //value的序列化
        redisTemplate.setValueSerializer(genericJackson2JsonRedisSerializer);
        //hash结构key的虚拟化
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        //hash结构value的虚拟化
        redisTemplate.setHashValueSerializer(genericJackson2JsonRedisSerializer);
        return redisTemplate;
    }
}