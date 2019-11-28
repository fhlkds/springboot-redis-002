package com.fhlkd.mapper;

import com.fhlkd.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.cache.annotation.CachePut;

/**
 * <p>
 * 员工表 Mapper 接口
 * </p>
 *
 * @author hy.Yang
 * @since 2019-11-27
 */
public interface SysUserMapper{


    void saveUser(SysUser sysUser);

    void updateUser(SysUser sysUser);

    SysUser listById(Integer id);

    void delUser(SysUser sysUser);
}
