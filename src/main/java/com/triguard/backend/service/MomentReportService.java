package com.triguard.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.triguard.backend.entity.dto.MomentReport;

public interface MomentReportService extends IService<MomentReport> {
    Boolean saveMomentReport(Integer accountId, Integer momentId, String reason);
}
