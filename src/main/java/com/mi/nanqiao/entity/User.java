package com.mi.nanqiao.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * (User)实体类
 *
 * @author makejava
 * @since 2023-01-18 18:05:18
 */
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 568945820627624341L;
    /**
     * 主键ID
     */
    private Integer id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 用户头像
     */
    private String avatar;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 性别
     */
    private String sex;
    /**
     * 地址
     */
    private String address;
    /**
     * 创建时间
     */
    private Date createTime;
}

