package com.fhlkd.controller;


import com.fhlkd.entity.SysUser;
import com.fhlkd.service.ISysUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

}
