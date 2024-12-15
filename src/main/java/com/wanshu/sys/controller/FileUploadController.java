package com.wanshu.sys.controller;


import com.wanshu.common.util.QiniuUploadUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping
@Api(tags = "文件上传")
public class FileUploadController {

    @Autowired
    private QiniuUploadUtil qiniuUploadUtil;

    /**
     * 上传单个文件到七牛云
     *
     * @param file 文件
     * @return 上传后的文件URL
     */
    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String url = qiniuUploadUtil.uploadFile(file);
            return url;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * 上传多个文件到七牛云
     *
     * @param files 文件列表
     * @return 上传后的文件URL列表
     */
    @PostMapping("/uploads")
    public List<String> uploadFiles(@RequestParam("files") List<MultipartFile> files) {
        try {
            List<String> urls = qiniuUploadUtil.uploadFiles(files);
            return urls;
        } catch (IOException e) {
            return null;
        }
    }
}
