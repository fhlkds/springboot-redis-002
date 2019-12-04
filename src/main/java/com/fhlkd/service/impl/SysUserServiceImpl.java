package com.fhlkd.service.impl;

import com.fhlkd.config.MyCache;
import com.fhlkd.constant.CompanyCacheConstant;
import com.fhlkd.entity.SysUser;
import com.fhlkd.mapper.SysUserMapper;
import com.fhlkd.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 员工表 服务实现类
 * </p>
 *
 * @author hy.Yang
 * @since 2019-11-27
 */
@Service
@Slf4j
@CacheConfig(cacheNames = CompanyCacheConstant.CACHE_NAME)
public class SysUserServiceImpl implements ISysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private MyCache myCache;



    // TODO: 2019/12/2 消费者服务使用例子
    //key:      {value}::{springCacheKeyGenreator}
    //业务场景所需  查询数据为null，不存缓存中
    @Override
    @Cacheable(keyGenerator = "springCacheKeyGenreator",unless = "#result==null")
    public SysUser listById(Integer id){
        //没有查询到数据 返回为null
        log.info("----------缓存中没有数据--------");
        return null;
    }


    // TODO: 2019/12/2  生产者使用例子，保存保存对象到缓存
    /**
     *  说明：保存时mybatis需要添加属性   useGeneratedKeys="true" keyProperty="id"
     * @param sysUser
     * @return
     */
    @Override
    @CachePut(keyGenerator = "springCacheKeyGenreator")
    public SysUser saveUser(SysUser sysUser) {
        //Cache cache = cacheManager.getCache("sysUser");
        sysUserMapper.saveUser(sysUser);
        return sysUser;
    }

    // TODO: 2019/12/2  生产者使用例子，更新对象保存对象到缓存
    /**
     * @param sysUser
     * @return
     */
    @Override
    @CachePut(keyGenerator = "springCacheKeyGenreator")
    public SysUser updateUser(SysUser sysUser) {
        log.info("更新前：{}",sysUser);
        int row = sysUserMapper.updateUser(sysUser);
        //更新数据,更新缓存需要把数据查询出来,然后保存到缓存中（防止部分更新导致数据丢失）
        sysUser = this.sysUserMapper.listById(sysUser.getId());
        return sysUser;
    }

    // TODO: 2019/12/2  生产者使用例子 删除，更新对象保存对象到缓存
    /**
     * ,beforeInvocation = false 默认方法执行之后删除缓存
     * @param sysUser
     */
    @Override
    @CacheEvict(key = "springCacheKeyGenreator")
    public void delUser(SysUser sysUser) {
        sysUserMapper.delUser(sysUser);
    }
    // TODO: 2019/12/2  生产者使用例子 批量修改，更新对象保存对象到缓存
    public void updateByCompanyId(SysUser sysUser){
        sysUserMapper.updateByCompanyId(sysUser);
        List list = sysUserMapper.selectList(SysUser.builder().companyId(sysUser.getCompanyId()).build());
        myCache.cachePutObjectList(CompanyCacheConstant.FIELDKEY,list);
    }


}
