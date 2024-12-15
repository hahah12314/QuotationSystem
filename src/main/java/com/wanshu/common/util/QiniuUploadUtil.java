package com.wanshu.common.util;


import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.wanshu.config.QiniuConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class QiniuUploadUtil {

    @Autowired
    private QiniuConfig qiniuConfig;

    private final UploadManager uploadManager;

    public QiniuUploadUtil() {
        this.uploadManager = new UploadManager(new Configuration(Region.autoRegion()));
    }

    /**
     * 上传单个文件到七牛云
     *
     * @param file 文件
     * @return 上传后的文件URL
     * @throws IOException 异常
     */
    public String uploadFile(MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        String fileName = qiniuConfig.getMkdir() + "/" + file.getOriginalFilename();
        String upToken = Auth.create(qiniuConfig.getAccessKey(), qiniuConfig.getSecretKey()).uploadToken(qiniuConfig.getBucket());
        try {
            Response response = uploadManager.put(bytes, fileName, upToken);
            if (response.isOK()) {
                return qiniuConfig.getDomain() + "/" + fileName;
            } else {
                throw new RuntimeException("上传失败: " + response.bodyString());
            }
        } catch (QiniuException e) {
            throw new RuntimeException("上传失败: " + e.response.toString());
        }
    }

    /**
     * 上传多个文件到七牛云
     *
     * @param files 文件列表
     * @return 上传后的文件URL列表
     * @throws IOException 异常
     */
    public List<String> uploadFiles(List<MultipartFile> files) throws IOException {
        List<String> urls = new ArrayList<>();
        for (MultipartFile file : files) {
            urls.add(uploadFile(file));
        }
        return urls;
    }
}
