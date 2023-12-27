package com.triguard.backend.entity.vo.response.Moment;

import lombok.Data;

import java.util.List;

@Data
public class MomentReportInfoVO {
    Integer momentId;
    String content;
    String images;
    String video;
    String createdAt;
    Integer reportCount;
    List<String> reportReasons;
}
