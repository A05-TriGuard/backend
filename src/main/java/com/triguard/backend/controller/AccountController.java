package com.triguard.backend.controller;

import com.triguard.backend.entity.RestBean;
import com.triguard.backend.entity.dto.Account;
import com.triguard.backend.entity.vo.response.Account.AccountInfoVO;
import com.triguard.backend.service.AccountService;
import com.triguard.backend.utils.ConstUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/account")
@Tag(name = "账户相关接口")
public class AccountController {

    @Resource
    AccountService accountService;

    /**
     * 获取账户信息
     *
     * @param request 请求
     * @return 账户信息
     */
    @GetMapping("/info")
    public RestBean<AccountInfoVO> getAccountInfo(HttpServletRequest request) {
        Integer accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        Account account = accountService.getById(accountId);
        AccountInfoVO accountInfoVO = new AccountInfoVO();
        BeanUtils.copyProperties(account, accountInfoVO);
        return RestBean.success(accountInfoVO);
    }

}
