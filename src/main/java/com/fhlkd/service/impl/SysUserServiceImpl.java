package com.fhlkd.service.impl;

import com.fhlkd.entity.SysUser;
import com.fhlkd.mapper.SysUserMapper;
import com.fhlkd.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

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

    //key:      {value}::{springCacheKeyGenreator}
    //业务场景所需  查询数据为null，不存缓存中
    @Override
    @Cacheable(value = "sysUser",keyGenerator = "springCacheKeyGenreator",unless = "#result==null")
    public SysUser listById(Integer id){
        //没有查询到数据 返回为null
        log.info("----------缓存中没有数据--------");
        return null;
    }

}
