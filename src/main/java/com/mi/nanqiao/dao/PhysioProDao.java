package com.mi.nanqiao.dao;

import com.mi.nanqiao.entity.PhysioPro;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * (PhysioPro)表数据库访问层
 *
 * @author makejava
 * @since 2023-01-26 12:16:41
 */
public interface PhysioProDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    PhysioPro queryById(Integer id);

    /**
     * 新增数据
     *
     * @param physioPro 实例对象
     * @return 影响行数
     */
    int insert(PhysioPro physioPro);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<PhysioPro> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<PhysioPro> entities);

    /**
     * 修改数据
     *
     * @param physioPro 实例对象
     * @return 影响行数
     */
    int update(PhysioPro physioPro);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    /**
     * 通过理疗师Id进行删除
     *
     * @param physioId 理疗师id
     * @return 影响行数
     */
    int deleteByPhysioId(Integer physioId);

    /**
     * 通过项目id进行删除
     *
     * @param projectId 项目id
     * @return 影响行数
     */
    int deleteByProjectId(Integer projectId);

    /**
     * 根据理疗师ID查询关联项目ID
     *
     * @param physioId 理疗师ID
     * @return 该理疗师对应的项目Id
     */
    @Select("select project_id from physio_pro where physio_id = #{physioId}")
    List<Integer> findProjectIdsByPhysioId(Integer physioId);

}

