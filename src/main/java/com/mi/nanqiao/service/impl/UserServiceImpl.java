package com.mi.nanqiao.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mi.nanqiao.common.CustomException;
import com.mi.nanqiao.config.RedisConstant;
import com.mi.nanqiao.dto.UserDTO;
import com.mi.nanqiao.entity.User;
import com.mi.nanqiao.dao.UserDao;
import com.mi.nanqiao.service.UserService;
import com.mi.nanqiao.utils.RedisUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * (User)表服务实现类
 *
 * @author makejava
 * @since 2023-01-18 18:05:20
 */
@Slf4j
@Service("userService")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserDao userDao;

    private final StringRedisTemplate stringRedisTemplate;


    @Override
    public User login(UserDTO userDTO) throws CustomException {
        User longinUser = null;
        String username = userDTO.getUsername();
        String code = userDTO.getCode();
        if (StringUtils.hasLength(username)){
            log.info("使用用户名登录");
            String redisCaptchaCode = RedisUtils.getString(RedisConstant.CAPTCHA + username);
            if (Objects.equals(code,redisCaptchaCode)){
                String password = userDTO.getPassword();
                String md5Password = SecureUtil.md5(password);
                List<User> users = userDao.findByUsername(username);
                for (User user : users) {
                    if (Objects.equals(md5Password,user.getPassword())){
                        log.info("用户名密码登录成功,用户名{}，密码{}",username,password);
                        longinUser = user;
                    }
                }
            }else {
                throw new CustomException("图像验证码错误");
            }
        }
        String phone = userDTO.getPhone();
        if (StringUtils.hasLength(phone)){
            log.info("使用手机号登录");
            String redisPhoneCode = RedisUtils.getString(RedisConstant.SMS_CODE + phone);
            if (Objects.equals(redisPhoneCode,code)){
                log.info("手机号验证码登录成功,手机号{}，验证码{}",phone,code);
                longinUser = userDao.findByPhone(phone);
            }
        }

        //将用户信息存入redis
        RedisUtils.setObject("user:"+longinUser.getId().toString(),longinUser,1,TimeUnit.HOURS);
        return longinUser;
    }

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public User queryById(Integer id) {
        return this.userDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param pageIndex 页码
     * @param pageSize  页大小
     * @return 查询结果
     */
    @Override
    public PageInfo<User> queryByPage(Integer pageIndex, Integer pageSize) {
        List<User> list = userDao.findAll();
        PageHelper.startPage(pageIndex, pageSize);
        return new PageInfo<>(list);
    }

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional
    public User insert(User user) {
        this.userDao.insert(user);
        return user;
    }

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional
    public User update(User user) {
        this.userDao.update(user);
        return this.queryById(user.getId());
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
        return this.userDao.deleteById(id) > 0;
    }
}
