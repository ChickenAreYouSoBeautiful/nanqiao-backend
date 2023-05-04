package com.mi.nanqiao.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 项目服务表(Project)实体类
 *
 * @author makejava
 * @since 2023-01-20 20:27:22
 */
@Data
public class Project implements Serializable {
    private static final long serialVersionUID = 350515485212349403L;
    /**
     * 主键ID
     */
    private Integer id;
    /**
     * 项目标题
     */
    private String title;
    /**
     * 项目图片
     */
    private String img;
    /**
     * 项目时长
     */
    private String duration;
    /**
     * 价格
     */
    private Integer price;
    /**
     * 消费人数
     */
    private Integer consumeCount;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    public Project() {
    }

    public Project(Integer id, String title, String img, String duration, Integer price, Integer consumeCount) {
        this.id = id;
        this.title = title;
        this.img = img;
        this.duration = duration;
        this.price = price;
        this.consumeCount = consumeCount;
        this.createTime = new Date();
    }
}

