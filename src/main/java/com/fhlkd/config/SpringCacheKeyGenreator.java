package com.fhlkd.config;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fhlkd.entity.SysUser;
import com.fhlkd.mapper.SysUserMapper;
import com.fhlkd.service.impl.SysUserServiceImpl;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.Duration;

/**
 * Created by yanghaiyang on 2019/11/26 19:06
 * redis 主键生成策略
 */
@Component
@EnableCaching
@SpringBootConfiguration
public class SpringCacheKeyGenreator implements KeyGenerator {

    private final static int NO_PARAM_KEY = 0;

    /**
     * 自定义主键生成策略根据不同对象生成不同主键
     * @param o
     * @param method
     * @param objects
     * @return
     */
    @Override
    public Object generate(Object o, Method method, Object... objects) {
        if(o instanceof SysUserMapper) {
            if(objects[0] instanceof SysUser){
                SysUser sysUser = (SysUser)objects[0];
                return "sysUser"+sysUser.getId();
            }
            return "sysUser" + objects[0].toString();
        }
        if(o instanceof SysUserServiceImpl){
            if(objects[0] instanceof SysUser){
                return "sysUser"+((SysUser)objects[0]).getId();
            }
            return "sysUser"+objects[0].toString();
        }
        /*if(o instanceof TestSysUserMapper)
            return "sysUser"+objects[0].toString();
        if(o instanceof TestUserServiceImpl)
            return "sysUser"+objects[0].toString();*/
        return null;
    }

    //CacheManager配置缓存 反参格式 前缀 过期时间
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
        RedisCacheConfiguration cacheConfiguration = config
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.string()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.json()))
                //.prefixKeysWith("project:")
                //永久缓存
                .entryTtl(Duration.ZERO);
        return RedisCacheManager
                .builder(factory)
                .cacheDefaults(cacheConfiguration)
                .build();



    }

    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory factory){
        RedisTemplate<String,Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);

        template.setConnectionFactory(factory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
              /*  Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        //key 采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        // hash的key也采用String的序列方式
        template.setHashKeySerializer(stringRedisSerializer);
        //vale也采用jackson的序列化方式
        template.setValueSerializer(jackson2JsonRedisSerializer);
        // hash的value采用jackson的序列化方式
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();*/
        return template;
    }

}
