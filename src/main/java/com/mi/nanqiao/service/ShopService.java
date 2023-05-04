package com.mi.nanqiao.service;

import com.github.pagehelper.PageInfo;
import com.mi.nanqiao.entity.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * (Shop)表服务接口
 *
 * @author makejava
 * @since 2023-01-29 15:13:51
 */
public interface ShopService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Shop queryById(Integer id);

    /**
     * 分页查询
     *
     * @param page 页数
     * @param size 页码
     * @return 查询结果
     */
    PageInfo<Shop> queryByPage(Integer page,Integer size);

    /**
     * 新增数据
     *
     * @param shop 实例对象
     * @return 实例对象
     */
    Shop insert(Shop shop);

    /**
     * 修改数据
     *
     * @param shop 实例对象
     * @return 实例对象
     */
    Shop update(Shop shop);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 查询商品名称
     *
     * @param lbid 商品类型id
     * @return 查询结构
     */
    Shop queryByLbid(Integer lbid);

}
