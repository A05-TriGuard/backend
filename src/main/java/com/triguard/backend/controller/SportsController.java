package com.triguard.backend.controller;

import com.triguard.backend.entity.RestBean;
import com.triguard.backend.service.StepsService;
import com.triguard.backend.utils.ConstUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 运动相关接口
 */
@Validated
@RestController
@RequestMapping("/api/sports")
@Tag(name = "运动相关接口")
public class SportsController {

    @Resource
    StepsService stepsService;

    @GetMapping("/steps")
    @Operation(summary = "获取今日步数")
    public RestBean<Integer> getTodaySteps(@RequestParam(required = false) Integer accountId,
                                           HttpServletRequest request) {
        if (accountId == null) {
            accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        }
        return RestBean.success(stepsService.getTodaySteps(accountId));
    }

    @PostMapping("/steps/update")
    @Operation(summary = "更新今日步数")
    public RestBean<Void> updateTodaySteps(@RequestParam Integer steps,
                                           @RequestParam(required = false) Integer accountId,
                                           HttpServletRequest request) {
        if (accountId == null) {
            accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        }
        return stepsService.updateTodaySteps(accountId, steps) ? RestBean.success() : RestBean.failure(400, "更新失败");
    }

}
