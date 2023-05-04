package com.mi.nanqiao.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * (PhysioPro)实体类
 *
 * @author makejava
 * @since 2023-01-26 12:16:42
 */
@Data
public class PhysioPro implements Serializable {
    private static final long serialVersionUID = 494330431572477920L;
    /**
     * 主键ID
     */
    private Integer id;
    /**
     * 理疗师ID
     */
    private Integer physioId;
    /**
     * 项目ID
     */
    private Integer projectId;
}

