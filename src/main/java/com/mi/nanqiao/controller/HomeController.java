package com.mi.nanqiao.controller;

import com.mi.nanqiao.entity.Project;
import com.mi.nanqiao.entity.R;
import com.mi.nanqiao.service.OrdersService;
import com.mi.nanqiao.service.PhysioService;
import com.mi.nanqiao.service.ProjectService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@Api(tags = "控制器服务")
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {

    private final ProjectService projectService;
    private final PhysioService physioService;
    private final OrdersService ordersService;

    @GetMapping
    public R queryStatics(){
        List<Project> list = projectService.list();
        long projectCount = list.stream().count();
        long physioCount = physioService.count();
        long ordersCount = ordersService.count();
        List<String> imgList = list.stream().map(Project::getImg).collect(Collectors.toList());
        HashMap<String, Object> data = new HashMap<>();
        data.put("projectCount",projectCount);

        data.put("ordersCount",ordersCount);
        data.put("imgList",imgList);
        return R.ok(data);
    }
}
