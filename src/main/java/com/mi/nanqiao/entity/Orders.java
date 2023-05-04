package com.mi.nanqiao.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 订单表(Orders)实体类
 *
 * @author makejava
 * @since 2023-01-30 19:42:16
 */
@Data
public class Orders implements Serializable {
    private static final long serialVersionUID = 780380622218962135L;

    private Integer id;
    /**
     * 用户ID
     */
    private Integer uid;
    /**
     * 总金额
     */
    private Double totalMoney;
    /**
     * 支付类型
     */
    private Integer payType;
    /**
     * 订单状态
     */
    private Integer flag;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy:MM:dd HH:ss:mm",timezone = "GMT+8")
    private Date createTime;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy:MM:dd HH:ss:mm",timezone = "GMT+8")
    private Date updateTime;
    /**
     * 订单编号
     */
    private String no;
    /**
     * 服务项目ID
     */
    private Integer projectId;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 理疗师ID
     */
    private Integer physioId;
    /**
     * 理疗师名称
     */
    private String physioName;

}

