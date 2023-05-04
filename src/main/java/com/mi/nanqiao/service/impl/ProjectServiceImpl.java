package com.mi.nanqiao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mi.nanqiao.entity.Project;
import com.mi.nanqiao.dao.ProjectDao;
import com.mi.nanqiao.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 项目服务表(Project)表服务实现类
 *
 * @author makejava
 * @since 2023-01-20 20:26:44
 */
@Slf4j
@Service("projectService")
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectDao projectDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Project queryById(Integer id) {
        return this.projectDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param size 每页有说少条数据
     * @param page 从第几页开始
     * @param title 项目名称
     * @return 查询结果
     */
    @Override
    public PageInfo<Project> queryByPage(Integer size,Integer page,String title) {
        PageHelper.startPage(page,size);
        List<Project> list = projectDao.queryAll(title);
        return new PageInfo<>(list);
    }

    /**
     * 新增数据
     *
     * @param project 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional
    public Project insert(Project project) {
        //初始化服务数
        project.setConsumeCount(0);
        //初始化创建时间
        project.setCreateTime(new Date());
        this.projectDao.insert(project);
        return project;
    }

    /**
     * 修改数据
     *
     * @param project 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional
    public Project update(Project project) {
        this.projectDao.update(project);
        return this.queryById(project.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    @Transactional
    public boolean deleteById(Integer id) {
        return this.projectDao.deleteById(id) > 0;
    }

    @Override
    public List<Project> list() {
        return projectDao.queryAll("");
    }
}
