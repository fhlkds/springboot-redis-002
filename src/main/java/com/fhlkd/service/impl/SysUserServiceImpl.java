package com.fhlkd.service.impl;

import com.fhlkd.entity.SysUser;
import com.fhlkd.mapper.SysUserMapper;
import com.fhlkd.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class SysUserServiceImpl implements ISysUserService {

    @Override
    @Cacheable(value = "sysUser",keyGenerator = "springCacheKeyGenreator")
    public SysUser listById(Integer id){
        //没有查询到数据 返回为null
        return null;
    }

}
