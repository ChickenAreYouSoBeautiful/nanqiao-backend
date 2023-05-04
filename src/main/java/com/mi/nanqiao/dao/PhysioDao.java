package com.mi.nanqiao.dao;

import com.mi.nanqiao.entity.Physio;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 理疗师表(Physio)表数据库访问层
 *
 * @author makejava
 * @since 2023-01-25 21:45:11
 */
public interface PhysioDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Physio queryById(Integer id);

    /**
     * 分页查询
     *
     * @param nickname 昵称
     * @return 对象列表
     */
    List<Physio> queryAllByLimit(String nickname);

    /**
     * 统计总行数
     *
     * @param physio 查询条件
     * @return 总行数
     */
    long count(Physio physio);

    /**
     * 新增数据
     *
     * @param physio 实例对象
     * @return 影响行数
     */
    int insert(Physio physio);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Physio> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Physio> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Physio> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<Physio> entities);

    /**
     * 修改数据
     *
     * @param physio 实例对象
     * @return 影响行数
     */
    int update(Physio physio);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */

    int deleteById(Integer id);

    /**
     * 根据项目id查询关联的理疗师
     *
     * @param projectId 项目id
     * @return 理疗师集合
     */
    List<Physio> queryByProject(Integer projectId);
}

