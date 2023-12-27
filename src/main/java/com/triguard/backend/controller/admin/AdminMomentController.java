package com.triguard.backend.controller.admin;

import com.triguard.backend.entity.RestBean;
import com.triguard.backend.entity.dto.Moment;
import com.triguard.backend.entity.dto.MomentReport;
import com.triguard.backend.entity.vo.response.Moment.MomentReportInfoVO;
import com.triguard.backend.service.MomentReportService;
import com.triguard.backend.service.MomentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Validated
@RestController
@RequestMapping("/api/admin/moment")
@Tag(name = "管理员动态相关接口")
public class AdminMomentController {

    @Resource
    MomentService momentService;

    @Resource
    MomentReportService momentReportService;

    /**
     * 获取动态举报信息
     *
     * @return MomentReportInfo
     */
    @GetMapping("/report")
    @Operation(summary = "获取动态举报信息")
    public RestBean<List<MomentReportInfoVO>> getMomentReportInfo() {
        List<MomentReport> momentReports = momentReportService.list();
        Map<Integer, MomentReportInfoVO> momentReportInfoVOMap = new HashMap<>();
        for (MomentReport momentReport : momentReports) {
            Integer momentId = momentReport.getMomentId();
            MomentReportInfoVO momentReportInfoVO = momentReportInfoVOMap.get(momentId);
            // 如果不存在则创建
            if (momentReportInfoVO == null) {
                momentReportInfoVO = new MomentReportInfoVO();
                momentReportInfoVO.setMomentId(momentId);
                momentReportInfoVO.setReportCount(0);
                momentReportInfoVO.setReportReasons(new ArrayList<>());
                Moment moment = momentService.getById(momentId);
                momentReportInfoVO.setContent(moment.getContent());
                momentReportInfoVO.setImages(moment.getImages());
                momentReportInfoVO.setVideo(moment.getVideo());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                momentReportInfoVO.setCreatedAt(simpleDateFormat.format(moment.getCreatedAt()));
                momentReportInfoVOMap.put(momentId, momentReportInfoVO);
            }
            // 更新举报统计和原因
            momentReportInfoVO.setReportCount(momentReportInfoVO.getReportCount() + 1);
            momentReportInfoVO.getReportReasons().add(momentReport.getReason());
        }
        List<MomentReportInfoVO> momentReportInfoVOS = new ArrayList<>(momentReportInfoVOMap.values());
        return RestBean.success(momentReportInfoVOS);
    }

    /**
     * 删除动态
     *
     * @param momentId 动态id
     * @return 是否删除成功
     */
    @GetMapping("/delete")
    @Operation(summary = "删除动态")
    public RestBean<Boolean> deleteMoment(@RequestParam Integer momentId) {
        return momentService.removeById(momentId) ? RestBean.success(true) : RestBean.failure(400, "删除失败");
    }

}
