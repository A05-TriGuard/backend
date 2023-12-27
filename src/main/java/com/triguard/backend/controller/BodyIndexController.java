package com.triguard.backend.controller;

import com.triguard.backend.entity.RestBean;
import com.triguard.backend.entity.dto.BodyIndex;
import com.triguard.backend.entity.vo.request.BodyIndex.BodyIndexSetVO;
import com.triguard.backend.service.BodyIndexService;
import com.triguard.backend.utils.ConstUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/body-index")
@Tag(name = "身体指标相关接口")
public class BodyIndexController {

    @Resource
    BodyIndexService bodyIndexService;

    /**
     * 设置身体指标
     * @param bodyIndexSetVO 身体指标VO
     * @param request 请求
     * @return 身体指标
     */
    @PostMapping("/set")
    @Operation(summary = "设置身体指标")
    public RestBean<BodyIndex> setBodyIndex(@RequestBody @Valid BodyIndexSetVO bodyIndexSetVO,
                                            HttpServletRequest request) {
        Integer accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        BodyIndex bodyIndex = bodyIndexService.setBodyIndex(accountId, bodyIndexSetVO);
        if (bodyIndex == null) {
            return RestBean.failure(400, "设置失败");
        } else {
            return RestBean.success(bodyIndex);
        }
    }

    /**
     * 获取身体指标
     * @param request 请求
     * @return 身体指标
     */
    @GetMapping("/get")
    @Operation(summary = "获取身体指标")
    public RestBean<BodyIndex> getBodyIndex(HttpServletRequest request) {
        Integer accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        BodyIndex bodyIndex = bodyIndexService.query().eq("account_id", accountId).one();
        if (bodyIndex == null) {
            return RestBean.success();
        } else {
            return RestBean.success(bodyIndex);
        }
    }

}
