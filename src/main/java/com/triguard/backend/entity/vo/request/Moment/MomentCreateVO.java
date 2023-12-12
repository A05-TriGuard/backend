package com.triguard.backend.entity.vo.request.Moment;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 用于创建动态的表单
 */
@Data
public class MomentCreateVO {
    String content;
    List<MultipartFile> images;
    MultipartFile video;
    @JsonAlias("class")
    String classification;
    String category;
}
