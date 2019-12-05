package com.fhlkd.service;

import com.fhlkd.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 员工表 服务类
 * </p>
 *
 * @author hy.Yang
 * @since 2019-11-27
 */
public interface ISysUserService{

    public SysUser listById(Integer id);

    SysUser saveUser(SysUser sysUser);

    SysUser updateUser(SysUser sysUser);

    void delUser(SysUser sysUser);
    SysUser saveUsers(SysUser sysUser);


    void updateByCompanyId(SysUser sysUser);
}
