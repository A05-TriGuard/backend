package com.triguard.backend.entity.vo.response.Authorization;

import lombok.Data;

import java.util.Date;

/**
 * 登录验证成功的用户信息响应
 */
@Data
public class AuthorizationVO {
    String username;
    String role;
    String token;
    Date expire;
}
