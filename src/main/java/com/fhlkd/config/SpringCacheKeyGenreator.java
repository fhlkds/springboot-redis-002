package com.fhlkd.config;


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
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
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
        if(o instanceof SysUserMapper)
            return "sysUser"+objects[0].toString();
        if(o instanceof SysUserServiceImpl)
            return "sysUser"+objects[0].toString();
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

}
