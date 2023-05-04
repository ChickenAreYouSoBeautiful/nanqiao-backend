package com.mi.nanqiao.dto;

import lombok.Data;

@Data
public class OrdersDto {

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

    /**
     * 用户ID
     */
    private Integer uid;
    /**
     * 总金额
     */
    private Double totalMoney;
}
