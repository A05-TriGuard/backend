package com.triguard.backend.entity.vo.response.Account;

import lombok.Data;

/**
 * 账户信息
 */
@Data
public class AccountInfoVO {
    Integer id;
    String username;
    String email;
    String phone;
}
