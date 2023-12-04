package com.triguard.backend.controller;

import com.triguard.backend.entity.RestBean;
import com.triguard.backend.entity.dto.Account;
import com.triguard.backend.entity.dto.Guard;
import com.triguard.backend.entity.vo.response.Guardian.GuardianInfoVO;
import com.triguard.backend.service.AccountService;
import com.triguard.backend.service.GuardService;
import com.triguard.backend.utils.ConstUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 监护系统相关API
 */
@Validated
@RestController
@RequestMapping("/api")
@Tag(name = "监护人相关", description = "监护系统的相关API")
public class GuardController {

    @Resource
    GuardService guardService;

    @Resource
    AccountService accountService;

    /**
     * 获取监护人列表
     * @param request HTTP请求
     * @return 监护人列表
     */
    @GetMapping("/guardian/list")
    @Operation(summary = "获取监护人列表", description = "获取监护人列表")
    public RestBean<List<GuardianInfoVO>> getGuardianList(HttpServletRequest request) {
        Integer wardId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        List<Guard> guardList = guardService.getGuardianList(wardId);
        List<GuardianInfoVO> guardianInfoVOS = guardList.stream().map(guard -> {
            GuardianInfoVO guardianInfoVO = new GuardianInfoVO();
            guardianInfoVO.setAccountId(guard.getGuardianId());
            guardianInfoVO.setNickname(guard.getGuardianNickname());
            Account account = accountService.getById(guard.getGuardianId());
            guardianInfoVO.setUsername(account.getUsername());
            guardianInfoVO.setEmail(account.getEmail());
            // TODO: set image
            return guardianInfoVO;
        }).toList();
        return RestBean.success(guardianInfoVOS);
    }

    /**
     * 删除监护人
     * @param accountId 用户ID
     * @param request HTTP请求
     * @return 删除结果
     */
    @GetMapping("/guardian/delete")
    @Operation(summary = "删除监护人", description = "删除监护人")
    public RestBean<String> deleteGuardian(@RequestParam Integer accountId,
                                           HttpServletRequest request) {
        Integer wardId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        String message = guardService.deleteGuardian(wardId, accountId);
        if (message != null) {
            return RestBean.failure(400, message);
        }
        return RestBean.success();
    }

    /**
     * 设置监护人昵称
     * @param accountId 用户ID
     * @param nickname 监护人昵称
     * @param request HTTP请求
     * @return 设置结果
     */
    @PostMapping("/guardian/set-nickname")
    @Operation(summary = "设置监护人昵称", description = "设置监护人昵称")
    public RestBean<String> setGuardianNickname(@RequestParam Integer accountId,
                                                @RequestParam String nickname,
                                                HttpServletRequest request) {
        Integer wardId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        String message = guardService.setGuardianNickname(wardId, accountId, nickname);
        if (message != null) {
            return RestBean.failure(400, message);
        }
        return RestBean.success();
    }

    /**
     * 邀请监护人
     * @param email 监护人邮箱
     * @param request HTTP请求
     * @return 邀请结果
     */
    @PostMapping("/guardian/invite")
    @Operation(summary = "邀请监护人", description = "邀请监护人")
    public RestBean<String> inviteGuardian(@RequestParam String email,
                                           HttpServletRequest request) {
        Integer wardId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        String message = guardService.inviteGuardian(wardId, email);
        if (message != null) {
            return RestBean.failure(400, message);
        }
        return RestBean.success();
    }

}
