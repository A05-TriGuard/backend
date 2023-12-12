package com.triguard.backend.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.triguard.backend.entity.dto.MomentReport;
import com.triguard.backend.mapper.MomentReportMapper;
import com.triguard.backend.service.MomentReportService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MomentReportServiceImpl extends ServiceImpl<MomentReportMapper, MomentReport> implements MomentReportService {

    /**
     * 举报动态
     *
     * @param accountId 举报人id
     * @param momentId  被举报动态id
     * @param reason    举报理由
     * @return 是否举报成功
     */
    public Boolean saveMomentReport(Integer accountId, Integer momentId, String reason) {
        MomentReport momentReport = new MomentReport();
        momentReport.setAccountId(accountId);
        momentReport.setMomentId(momentId);
        momentReport.setReason(reason);
        momentReport.setCreateTime(new Date());
        return this.save(momentReport);
    }

}
