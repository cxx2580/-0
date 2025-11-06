package com.card.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class FileUtil {
    @Value("${upload.path}")
    private String uploadPath;

    @Value("${upload.access-path}")
    private String accessPath;

    // 上传文件（返回文件访问路径）
    public String uploadFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return null;
        }
        // 创建上传目录（不存在则创建）
        File dir = new File(uploadPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        // 生成唯一文件名（避免重复）
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = UUID.randomUUID().toString() + suffix;
        // 保存文件
        file.transferTo(new File(uploadPath + fileName));
        // 返回访问路径
        return accessPath + fileName;
    }
}