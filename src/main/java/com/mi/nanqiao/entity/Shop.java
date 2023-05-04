package com.mi.nanqiao.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * (Shop)实体类
 *
 * @author makejava
 * @since 2023-01-29 15:13:49
 */
@Data
public class Shop implements Serializable {
    private static final long serialVersionUID = -57058620982947730L;
    /**
     * 主键
     */
    private Integer id;
    /**
     * 商品名称
     */
    private String spname;
    /**
     * 商品价格
     */
    private Integer spjiage;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date spaddtime;
    /**
     * 商品图片
     */
    private String sptupian;
    /**
     * 商品类型关联
     */
    private Integer lbid;

}

