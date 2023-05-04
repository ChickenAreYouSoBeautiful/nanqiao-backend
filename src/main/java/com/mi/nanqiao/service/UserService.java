package com.mi.nanqiao.service;

import com.github.pagehelper.PageInfo;
import com.mi.nanqiao.common.CustomException;
import com.mi.nanqiao.dto.UserDTO;
import com.mi.nanqiao.entity.R;
import com.mi.nanqiao.entity.User;


/**
 * (User)表服务接口
 *
 * @author makejava
 * @since 2023-01-18 18:05:19
 */
public interface UserService {

    /**
     * 用户登录
     * @param userDTO 封装的登录对象
     * @return 实例对象
     */
    User login(UserDTO userDTO) throws CustomException;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    User queryById(Integer id);

    /**
     * 分页查询
     *
     * @param pageIndex 页码
     * @param pageSize  页大小
     * @return 查询结果
     */
    PageInfo<User> queryByPage(Integer pageIndex, Integer pageSize);

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    User insert(User user);

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    User update(User user);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
