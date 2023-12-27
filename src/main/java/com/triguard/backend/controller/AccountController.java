package com.triguard.backend.controller;

import com.triguard.backend.entity.RestBean;
import com.triguard.backend.entity.dto.Account;
import com.triguard.backend.entity.vo.response.Account.AccountInfoVO;
import com.triguard.backend.service.AccountService;
import com.triguard.backend.utils.ConstUtils;
import com.triguard.backend.utils.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Validated
@RestController
@RequestMapping("/api/account")
@Tag(name = "账户相关接口")
public class AccountController {

    @Resource
    AccountService accountService;

    @Resource
    JwtUtils jwtUtils;

    /**
     * 获取账户信息
     *
     * @param request 请求
     * @return 账户信息
     */
    @GetMapping("/info")
    @Operation(summary = "获取账户信息")
    public RestBean<AccountInfoVO> getAccountInfo(HttpServletRequest request) {
        Integer accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        Account account = accountService.getById(accountId);
        AccountInfoVO accountInfoVO = new AccountInfoVO();
        BeanUtils.copyProperties(account, accountInfoVO);
        if (account.getProfile() == null) {
            accountInfoVO.setProfile(ConstUtils.DEFAULT_AVATAR);
        }
        return RestBean.success(accountInfoVO);
    }

    /**
     * 使用jwt登录账户，并进行续签
     *
     * @param request 请求
     * @return 账户信息
     */
    @GetMapping("/login")
    @Operation(summary = "使用jwt登录账户，并进行续签")
    public RestBean<String> login(HttpServletRequest request) {
        Integer accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        Account account = accountService.getById(accountId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            User user = (User) authentication.getPrincipal();
            String newToken = jwtUtils.createJwt(user, account.getUsername(), accountId);
            String oldToken = request.getHeader("Authorization");
            if (jwtUtils.invalidateJwt(oldToken)) {
                return RestBean.success(newToken);
            } else {
                return RestBean.failure(400, "登录失败");
            }
        } else {
            return RestBean.failure(400, "登录失败");
        }
    }

    /**
     * 设置账户头像
     */
    @PostMapping("/set-profile")
    @Operation(summary = "设置账户头像")
    public RestBean<String> setProfile(@RequestPart MultipartFile profile,
                                     HttpServletRequest request) {
        Integer accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        String url = accountService.setProfile(accountId, profile);
        if (url == null) {
            return RestBean.failure(400, "上传失败");
        } else {
            return RestBean.success(url);
        }
    }

}
