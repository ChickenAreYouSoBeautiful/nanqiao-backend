package com.mi.nanqiao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mi.nanqiao.entity.Shop;
import com.mi.nanqiao.dao.ShopDao;
import com.mi.nanqiao.service.ShopService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * (Shop)表服务实现类
 *
 * @author makejava
 * @since 2023-01-29 15:13:51
 */
@Service("shopService")
public class ShopServiceImpl implements ShopService {
    @Resource
    private ShopDao shopDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Shop queryById(Integer id) {
        return this.shopDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param page 页数
     * @param size 页码
     * @return 查询结果
     */
    @Override
    public PageInfo<Shop> queryByPage(Integer page,Integer size) {
        PageHelper.startPage(page,size);
        List<Shop> shops = shopDao.queryAll();
        return new  PageInfo<Shop>(shops);
    }

    /**
     * 新增数据
     *
     * @param shop 实例对象
     * @return 实例对象
     */
    @Override
    public Shop insert(Shop shop) {
        shop.setSpaddtime(new Date());
        this.shopDao.insert(shop);
        return shop;
    }

    /**
     * 修改数据
     *
     * @param shop 实例对象
     * @return 实例对象
     */
    @Override
    public Shop update(Shop shop) {
        this.shopDao.update(shop);
        return this.queryById(shop.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.shopDao.deleteById(id) > 0;
    }

    /**
     * 查询商品名称
     *
     * @param lbid 商品类型id
     * @return 查询结构
     */
    @Override
    public Shop queryByLbid(Integer lbid) {
        return shopDao.querySpnameByLbid(lbid);
    }
}
