package com.triguard.backend.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    String uploadMultipartFile(MultipartFile file);

}
