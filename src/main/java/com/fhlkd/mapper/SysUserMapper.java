package com.fhlkd.mapper;

import com.fhlkd.entity.SysUser;

import java.util.List;

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

    int updateUser(SysUser sysUser);

    SysUser listById(Integer id);

    void delUser(SysUser sysUser);

    List<SysUser> selectList(SysUser sysUser);

    void updateByCompanyId(SysUser sysUser);
}
