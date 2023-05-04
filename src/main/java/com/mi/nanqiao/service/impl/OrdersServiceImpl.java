package com.mi.nanqiao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mi.nanqiao.common.OrderState;
import com.mi.nanqiao.common.PayType;
import com.mi.nanqiao.dao.PhysioDao;
import com.mi.nanqiao.dao.PhysioProDao;
import com.mi.nanqiao.dao.ProjectDao;
import com.mi.nanqiao.dto.OrdersDto;
import com.mi.nanqiao.entity.*;
import com.mi.nanqiao.dao.OrdersDao;
import com.mi.nanqiao.service.OrdersService;
import com.mi.nanqiao.service.PhysioService;
import com.mi.nanqiao.utils.StringUtil;
import com.mi.nanqiao.utils.ThreadLocalUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 订单表(Orders)表服务实现类
 *
 * @author makejava
 * @since 2023-01-30 19:42:17
 */
@Slf4j
@RequiredArgsConstructor
@Service("ordersService")
public class OrdersServiceImpl implements OrdersService {

    private final OrdersDao ordersDao;

    private final ProjectDao projectDao;

    private final PhysioDao physioDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Orders queryById(Integer id) {
        return this.ordersDao.queryById(id);
    }

    /**
     * 根据用户信息分页查询订单
     *
     * @param page 页码
     * @param size 页大小
     * @return 查询结果
     */
   @Override
   public PageInfo<Orders> queryByPage(Integer page, Integer size) {
       List<Orders> orders = new ArrayList<>();
       User user = (User) ThreadLocalUtils.get("user");
       PageHelper.startPage(page,size);
       if (Objects.nonNull(user)){
           orders = ordersDao.queryAllByPage(user.getId());
       }
       return new  PageInfo<Orders>(orders);
   }

    /**
     * 新增数据
     *
     * @param ordersDto 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional
    public Orders insert(OrdersDto ordersDto) {
        Orders orders = new Orders();
        BeanUtils.copyProperties(ordersDto,orders);
        //增加属性
        orders.setCreateTime(new Date());
        orders.setNo(StringUtil.getUUID());
        orders.setFlag(OrderState.UNPAID.getCode());
        //补全用户数据
        User user = (User) ThreadLocalUtils.get("user");
        assert user != null;
        orders.setUid(user.getId());
        if(ordersDao.insert(orders) >0) {
            return orders;
        }
       return null;
    }

    /**
     * 修改数据
     *
     * @param orders 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional
    public Orders update(Orders orders) {
        this.ordersDao.update(orders);
        return this.queryById(orders.getId());
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
        return this.ordersDao.deleteById(id) > 0;
    }

    @Override
    public Orders queryByNo(String code) {
        return ordersDao.queryByNo(code);
    }

    @Override
    @Transactional
    public boolean orderPay(String no) {
        Orders orders = this.queryByNo(no);
        if (Objects.nonNull(orders)){
            //修改支付状态
            orders.setFlag(OrderState.PAID.getCode());
            //修改支付方式
            orders.setPayType(PayType.ALI_PAY.getCode());
            orders.setUpdateTime(new Date());
            this.update(orders);
            //添加理疗师服务单数
            Integer physioId = orders.getPhysioId();
            Physio physio = physioDao.queryById(physioId);
            if (Objects.nonNull(physio)){
                //添加服务单数
                physio.setBillCount(physio.getBillCount()+1);
                physioDao.update(physio);
                //修改项目消费人数
                Integer projectId = orders.getProjectId();
                Project project = projectDao.queryById(projectId);
                if (Objects.nonNull(project)){
                    project.setConsumeCount(project.getConsumeCount()+1);
                    projectDao.update(project);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public long count() {
        return ordersDao.count(new Orders());
    }
}
