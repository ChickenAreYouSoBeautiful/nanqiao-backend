package com.mi.nanqiao.controller;


import com.mi.nanqiao.service.impl.QRServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Api(tags = "二维码接口")
@RequestMapping("qrcode")
public class QRController {
    private final QRServiceImpl qrService;

    /**
     * 生成二维码图片放入响应流中
     * @param content 文字内容
     * @param servletResponse
     * @throws IOException
     */
    @ApiOperation("生成二维码图片放入响应流中")
    @ApiImplicitParam(name = "content",value = "文字内容",required = true)
    @RequestMapping(method = {RequestMethod.GET,RequestMethod.POST})
    public void generateV3(String content, HttpServletResponse servletResponse) throws IOException {
        qrService.generateStream(content, servletResponse);
    }

    /**
     * 生成base64图片
     * @param content 文字内容
     * @return 图片的base64编码
     */
    @ApiOperation(value = "生成base64图片")
    @ApiImplicitParam(name = "content",value = "文字内容",required = true)
    @RequestMapping(value = "base64",method = {RequestMethod.GET,RequestMethod.POST})
    public String generateV3(String content) {
        return qrService.generateBase64(content);
    }

}
