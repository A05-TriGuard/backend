package com.triguard.backend.controller.admin;

import com.triguard.backend.entity.RestBean;
import com.triguard.backend.entity.dto.Account;
import com.triguard.backend.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Validated
@RestController
@RequestMapping("/api/admin/account")
@Tag(name = "管理员账户相关接口")
public class AdminAccountController {

    @Resource
    AccountService accountService;

    /**
     * 获取管理员列表
     *
     * @return 管理员列表
     */
    @PostMapping("/list")
    @Operation(summary = "获取管理员列表")
    public RestBean<List<Account>> getAccountList() {
        return RestBean.success(accountService.listByMap(Map.of("role", "admin")));
    }
}
