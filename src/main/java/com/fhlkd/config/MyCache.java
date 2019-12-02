package com.fhlkd.config;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by yanghaiyang on 2019/12/2 14:38
 * 解决部分更新及删除调用查询的多余开销及代码冗余
 * 缓存数据结构
 *  cacheName(sysUser)
 *      key(sysUser{#id}) Object
 */
@Component
public class MyCache {
    @Value("${spring.application.name:sysUser}")
    private String serverName;

    @Autowired
    private CacheManager cacheManager;

    /**
     * 一个对象保存到指定缓存中
     * @param key   key值
     * @param o 缓存的对象
     */
    public int cachePutObject(String key, Object o){
        Cache cache = cacheManager.getCache(serverName);
        cache.put(key,o);
        return 1;
    }

    /**
     *一个对象集合保存到指定缓存中
     *  注意保存的对象必须有id
     * @param keyPrefix key前缀
     * @param objects   保存的对象集合
     * @param
     */
    public int cachePutObjectList(String keyPrefix, List objects){
        int row = 0;
        for (Object t : objects){
            JSONObject jsonObject = JSONObject.fromObject(t);
            String id = jsonObject.get("id").toString();
            this.cachePutObject(keyPrefix+id,t);
            ++row;
        }
        return row;
    }

    public int cacheEvictObject(String key){
        Cache cache = cacheManager.getCache(serverName);
        cache.evict(key);
        return 1;
    }
    public int cacheEvictObjectList(String keyPrefix,List<Object> objects){
        int row = 0;
        Cache cache = cacheManager.getCache(serverName);
        for (Object t : objects){
            JSONObject jsonObject = JSONObject.fromObject(t);
            String id = jsonObject.get("id").toString();
            this.cacheEvictObject(keyPrefix+id);
            ++row;
        }
        return row;
    }

    public static void main(String[] args) {
        int i = 0;
        System.out.println(++i);
    }
}
