package com.mi.nanqiao.controller;


import com.mi.nanqiao.config.MyMinioProperties;
import com.mi.nanqiao.entity.R;
import com.mi.nanqiao.service.MinioService;
import com.mi.nanqiao.utils.FileTypeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;


/**
 * 文件控制器
 *
 * @author zed
 * @date 2022/11/24
 */
@Slf4j
@RestController
@Api(tags = "文件控制器")
@RequiredArgsConstructor
@RequestMapping("file")
public class FileController {
    private final MinioService minioService;

    private final MyMinioProperties minioProperties;

    /**
     * 文件上传接口
     * @param file
     * @return
     */
    @ApiOperation("文件上传接口")
    @PostMapping("upload")
    public R<String> fileUpload(MultipartFile file) {
        if (Objects.isNull(file)) {
            return R.fail("上传的文件是空的");
        }
        // 判断文件类型
        String fileType = FileTypeUtils.getFileType(file);
        if (!StringUtils.hasLength(fileType)) {
            return R.fail("文件格式不支持");
        }
        // 进行文件上传
        // 三个参数：1、文件对象 2、存储桶名字 3、文件类型
        log.info("开始文件上传，文件类型是{}", fileType);
        String url = minioService.putObject(file, minioProperties.getBucketName(), fileType);
        return R.ok(url);
    }
}
