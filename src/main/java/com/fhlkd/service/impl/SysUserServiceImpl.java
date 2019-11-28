package com.fhlkd.service.impl;

import com.fhlkd.entity.SysUser;
import com.fhlkd.mapper.SysUserMapper;
import com.fhlkd.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
public class SysUserServiceImpl implements ISysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    //key:      {value}::{springCacheKeyGenreator}
    //业务场景所需  查询数据为null，不存缓存中
    @Override
    @Cacheable(value = "sysUser",keyGenerator = "springCacheKeyGenreator",unless = "#result==null")
    public SysUser listById(Integer id){
        //没有查询到数据 返回为null
        log.info("----------缓存中没有数据--------");
        return null;
    }

    /**
     * 极大不方便之处 缓存的是方法结果，进而需要写service层
     *  说明：保存时mybatis需要添加属性   useGeneratedKeys="true" keyProperty="id" 主键赋值给对象
     * @param sysUser
     * @return
     */
    @Override
    @CachePut(value = "sysUser",keyGenerator = "springCacheKeyGenreator")
    public SysUser saveUser(SysUser sysUser) {
        sysUserMapper.saveUser(sysUser);
        return sysUser;
    }

    /**
     * 极大不方便之处  更新需要去查询一次  因而需要写service层
     * @param sysUser
     * @return
     */
    @Override
    @CachePut(value = "sysUser",keyGenerator = "springCacheKeyGenreator")
    public SysUser updateUser(SysUser sysUser) {
        log.info("更新前：{}",sysUser);
         sysUserMapper.updateUser(sysUser);
        return sysUserMapper.listById(sysUser.getId());
    }

    /**
     * ,beforeInvocation = false 默认方法执行之后清楚缓存
     * @param sysUser
     */
    @Override
    @CacheEvict(value = "sysUser",keyGenerator = "springCacheKeyGenreator")
    public void delUser(SysUser sysUser) {
        sysUserMapper.delUser(sysUser);
    }


}
