package com.mi.nanqiao.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 理疗师表(Physio)实体类
 *
 * @author makejava
 * @since 2023-01-25 21:45:12
 */
@Data
public class Physio implements Serializable {
    private static final long serialVersionUID = 242097273337358190L;
    /**
     * 主键ID
     */
    private Integer id;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 服务状态
     */
    private Integer state;
    /**
     * 点赞数
     */
    private Integer like;
    /**
     * 服务单数
     */
    private Integer billCount;

}

