package com.fhlkd.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 员工表
 * </p>
 *
 * @author hy.Yang
 * @since 2019-11-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 员工id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 员工名称
     */
    private String userName;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 员工真实姓名
     */
    private String realName;

    /**
     * 手机号
     */
    private String telephone;

    /**
     * 邮箱
     */
    private String mail;

    /**
     * 加密后的密码
     */
    private String password;

    /**
     * 员工所在公司的id
     */
    private Integer companyId;

    /**
     * 状态，1：正常，0：冻结状态，2：删除
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

    private String operator;

    /**
     * 最后一次更新时间
     */
    private Date operateTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后一次更新者的ip地址
     */
    private String operateIp;


}
