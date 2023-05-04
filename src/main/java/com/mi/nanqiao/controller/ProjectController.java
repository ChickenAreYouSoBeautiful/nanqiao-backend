package com.mi.nanqiao.controller;

import com.github.pagehelper.PageInfo;
import com.mi.nanqiao.entity.Project;
import com.mi.nanqiao.entity.R;
import com.mi.nanqiao.service.ProjectService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author mi11
 */
@RestController
@Api(tags = "服务项目接口")
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping("/insert")
    @ApiOperation("新增服务项目")
    public R<Project> insert(@RequestBody Project project){
        Assert.hasLength(project.getImg(),"项目图片不能为空");
        Assert.hasLength(project.getDuration(),"项目时长不能为空");
        Assert.hasLength(project.getTitle(),"项目标题不能为空");
        Assert.notNull(project.getPrice(),"项目价格不能为空");
        Project insert = projectService.insert(project);
        return R.ok(insert);
    }

    @PostMapping ("/page")
    @ApiOperation("分页查询服务项目")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "page",value = "页码"),
            @ApiImplicitParam(name = "size",value = "每页多少条"),
            @ApiImplicitParam(name = "title",value = "项目名称")
    })
    public R<PageInfo<Project>> queryByPage(@RequestParam(name = "page",defaultValue = "1",required = false) Integer page,
                         @RequestParam(name = "size",defaultValue = "10",required = false) Integer size,
                                            String title){
        PageInfo<Project> pageInfo = projectService.queryByPage(size, page,title);
        return R.ok(pageInfo);
    }

    @GetMapping("{id}")
    @ApiOperation("根基ID查询项目")
    public R<Project> queryById(@PathVariable Integer id){
        Assert.notNull(id,"id不能为空");
        return R.ok(projectService.queryById(id));
    }

    @PostMapping("update")
    @ApiOperation("根基ID更新项目")
    public R<Project> update(@RequestBody Project project){
        Assert.notNull(project.getId(),"id不能为空");
        return R.ok(projectService.update(project));
    }

    @DeleteMapping("{id}")
    @ApiOperation("根基ID删除项目")
    public R delete(@PathVariable Integer id){
        Assert.notNull(id,"id不能为空");
        return R.ok(projectService.deleteById(id));
    }

    @GetMapping("/list")
    public R<List<Project>> list(){
        List<Project> list = projectService.list();
        return R.ok(list);
    }
}
