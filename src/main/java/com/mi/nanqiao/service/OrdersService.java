package com.mi.nanqiao.service;

import com.github.pagehelper.PageInfo;
import com.mi.nanqiao.dto.OrdersDto;
import com.mi.nanqiao.entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 订单表(Orders)表服务接口
 *
 * @author makejava
 * @since 2023-01-30 19:42:17
 */
public interface OrdersService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Orders queryById(Integer id);

    /**
     * 根据用户信息分页查询订单
     *
     * @param page 页码
     * @param size 页大小
     * @return 查询结果
     */
    PageInfo<Orders> queryByPage(Integer page,Integer size);

    /**
     * 新增数据
     *
     * @param ordersDto 实例对象
     * @return 实例对象
     */
    Orders insert(OrdersDto ordersDto);

    /**
     * 修改数据
     *
     * @param orders 实例对象
     * @return 实例对象
     */
    Orders update(Orders orders);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 根据订单编号查循订单
     *
     * @param code 订单编号
     * @return 订单
     */
    Orders queryByNo(String code);


    /**
     * 支付成功修改状态
     *
     * @param no 订单编号
     * @return 成功
     */
    boolean orderPay(String no);

    /**
     * 统计总行数
     * @return 总行数
     */
    long count();
}
