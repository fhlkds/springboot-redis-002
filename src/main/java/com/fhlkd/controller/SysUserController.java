package com.fhlkd.controller;


import com.fhlkd.entity.SysUser;
import com.fhlkd.service.ISysUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 员工表 前端控制器
 * </p>
 *
 * @author hy.Yang
 * @since 2019-11-27
 */
@RestController
@RequestMapping("/sys-user")
public class SysUserController {

    @Resource
    private ISysUserService sysUserService;

    @PostMapping("/listById")
    public SysUser listByid(@RequestParam Integer id){
        return sysUserService.listById(id);

    }

    @PostMapping("/saveUser")
    public void saveUser(@RequestBody SysUser sysUser){
        sysUserService.saveUser(sysUser);
    }
    @PostMapping("/updateUser")
    public void updateUser(@RequestBody SysUser sysUser){
        sysUserService.updateUser(sysUser);
    }
    @PostMapping("/delUser")
    public void delUser(@RequestBody SysUser sysUser){
        sysUserService.delUser(sysUser);
    }

}
