package com.mi.nanqiao.controller;

import com.github.pagehelper.PageInfo;
import com.mi.nanqiao.dao.OrdersDao;
import com.mi.nanqiao.dto.OrdersDto;
import com.mi.nanqiao.entity.Orders;
import com.mi.nanqiao.entity.R;
import com.mi.nanqiao.entity.User;
import com.mi.nanqiao.service.OrdersService;
import com.mi.nanqiao.utils.ThreadLocalUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

@Api(tags = "订单服务")
@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrdersController {

    private final OrdersService ordersService;

    @PostMapping("/insert")
    @ApiOperation("马上预约")
    public R insert(@RequestBody OrdersDto ordersDto){
        Assert.notNull(ordersDto.getPhysioId(),"理疗师ID不能为空");
        Assert.notNull(ordersDto.getProjectId(),"项目ID不能为空");
        Assert.hasLength(ordersDto.getPhysioName(),"理疗师昵称不能为空");
        Assert.hasLength(ordersDto.getProjectName(),"项目名称不能为空");
        return R.ok(ordersService.insert(ordersDto));
    }

    @ApiOperation("根据ID查找订单")
    @ApiImplicitParam(name = "id",value = "订单ID")
    @GetMapping("/{id}")
    public R queryById(@PathVariable Integer id){
        Assert.notNull(id,"id不能为空");
        Orders orders = ordersService.queryById(id);
        return R.ok(orders);
    }

    @ApiOperation("分页查找订单")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "page",value = "页码"),
            @ApiImplicitParam(name = "size",value = "每页多少条"),
    })
    @PostMapping("page")
    public R queryByPage(@RequestParam(value = "page",defaultValue = "1",required = false) Integer page,
                         @RequestParam(value = "size",defaultValue = "10",required = false) Integer size){
        PageInfo<Orders> ordersPageInfo = ordersService.queryByPage(page, size);
        return R.ok(ordersPageInfo);
    }

    @ApiOperation("根据Id删除订单")
    @ApiImplicitParam(name = "id",value = "订单ID")
    @DeleteMapping("{id}")
    public R deleteById(@PathVariable Integer id){
        Assert.notNull(id,"ID不能为空");
        if (ordersService.deleteById(id)) {
            return R.ok();
        }
        return R.fail();
    }

}
