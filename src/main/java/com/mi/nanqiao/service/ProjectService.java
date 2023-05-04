package com.mi.nanqiao.service;

import com.github.pagehelper.PageInfo;
import com.mi.nanqiao.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * 项目服务表(Project)表服务接口
 *
 * @author makejava
 * @since 2023-01-20 20:26:44
 */
public interface ProjectService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Project queryById(Integer id);

    /**
     * 分页查询
     *
     * @param size 每页有说少条数据
     * @param page 从第几页开始
     * @param title 项目名称
     * @return 查询结果
     */
    PageInfo<Project> queryByPage(Integer size,Integer page,String title);

    /**
     * 新增数据
     *
     * @param project 实例对象
     * @return 实例对象
     */
    Project insert(Project project);

    /**
     * 修改数据
     *
     * @param project 实例对象
     * @return 实例对象
     */
    Project update(Project project);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 查询所有项目
     *
     * @return 项目集合
     */
    List<Project> list();

}
