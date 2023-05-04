package com.mi.nanqiao.service;

import com.github.pagehelper.PageInfo;

import com.mi.nanqiao.dto.PhysioDTO;
import com.mi.nanqiao.entity.Physio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * 理疗师表(Physio)表服务接口
 *
 * @author makejava
 * @since 2023-01-25 21:45:13
 */
public interface PhysioService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    PhysioDTO queryById(Integer id);

    /**
     * 分页查询
     *
     * @param page 分多少页
     * @param size 每页多少条数据
     * @param nickname 昵称
     * @return 查询结果
     */
    PageInfo<Physio> queryByPage(Integer page, Integer size, String nickname);

    /**
     * 新增数据
     *
     * @param physioDTO 实例对象
     * @return 实例对象
     */
    Physio insert(PhysioDTO physioDTO);

    /**
     * 修改数据
     *
     * @param physioDTO 实例对象
     * @return 实例对象
     */
    PhysioDTO update(PhysioDTO physioDTO);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 根据项目id查询关联的理疗师
     *
     * @param projectId 项目id
     * @return 理疗师集合
     */
    List<Physio> queryByProject(Integer projectId);

    /**
     * 统计总行数
     * @return 总行数
     */
    long count();
}
