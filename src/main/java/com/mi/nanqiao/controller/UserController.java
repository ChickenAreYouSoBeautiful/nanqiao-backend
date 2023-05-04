package com.mi.nanqiao.controller;

import cn.hutool.core.util.RandomUtil;
import com.mi.nanqiao.common.CustomException;
import com.mi.nanqiao.config.RedisConstant;
import com.mi.nanqiao.dto.UserDTO;
import com.mi.nanqiao.entity.R;
import com.mi.nanqiao.entity.User;
import com.mi.nanqiao.service.UserService;
import com.mi.nanqiao.utils.JwtUtils;
import com.mi.nanqiao.utils.RedisUtils;
import com.mi.nanqiao.utils.SmsUtils;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.wf.captcha.ArithmeticCaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@RestController
@RequestMapping("/user")
@Api(tags = "用户接口")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final SmsUtils smsUtils;

    @GetMapping("/captcha")
    @ApiOperation("获取图形验证码")
    @ApiImplicitParam(name = "username",value = "用户名")
    public void getCaptcha(String username, HttpServletResponse response) throws IOException {
        ArithmeticCaptcha arithmeticCaptcha = new ArithmeticCaptcha();
        String code = arithmeticCaptcha.text();
        RedisUtils.setString(RedisConstant.CAPTCHA+username,code,300);
        response.setContentType("image/jpeg");
        arithmeticCaptcha.out(response.getOutputStream());
    }

    @GetMapping("/sms")
    @ApiOperation("获取手机号验证码")
    @ApiImplicitParam(name = "phone",value = "手机号")
    public void sms(@RequestParam("phone") String phone) throws TencentCloudSDKException {
        Assert.hasLength(phone,"手机号不能为空");
        String code = RandomUtil.randomNumbers(6);
        //smsUtils.sendSms(new String[]{code},new String[]{phone});
        System.out.println("code= "+code);
        RedisUtils.setString(RedisConstant.SMS_CODE+phone,code,300);
    }


    @PostMapping("/login")
    @ApiOperation("用户登录")
    public R login(@RequestBody UserDTO userDTO,HttpServletResponse response) throws CustomException {
        String username = userDTO.getUsername();
        String phone = userDTO.getPhone();
        if (!StringUtils.hasLength(username) && !StringUtils.hasLength(phone)){
            throw new CustomException("用户名和手机号不能都为空");
        }
        if (StringUtils.hasLength(userDTO.getUsername())){
            if (!StringUtils.hasLength(userDTO.getPassword())){
                throw new CustomException("用户名和密码不能为空");
            }
        }

        if(StringUtils.hasLength(userDTO.getPhone())){
            if (StringUtils.hasLength(userDTO.getCode())){
                return R.fail("手机号和验证码不能为空");
            }
        }

        User user = userService.login(userDTO);
        if (Objects.isNull(user)){
            return R.fail("登录失败");
        }

        //生成token返回
        String token = JwtUtils.createToken(user.getId().toString(), user.getUsername());
        Cookie cookie = new Cookie("token", token);
        cookie.setPath("/");
        response.setHeader("token",token);
        response.addCookie(cookie);
        return R.ok(user);
    }

}
