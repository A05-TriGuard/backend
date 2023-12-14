package com.triguard.backend.service.Impl;

import com.triguard.backend.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileServiceImpl implements FileService {

    public String uploadMultipartFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new RuntimeException("文件为空");
        }
        try {
            String originalFilename = file.getOriginalFilename();
            String fileName = System.currentTimeMillis() + originalFilename;
            File staticDir = new File("static");
            String filePath = staticDir.getAbsolutePath() + "/" + fileName;
            File dest = new File(filePath);
            file.transferTo(dest);
            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("文件上传失败", e);
        }
    }

}
