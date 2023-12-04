package com.triguard.backend.entity.vo.response.Guardian;

import lombok.Data;

/**
 * 监护人信息返回VO
 */
@Data
public class GuardianInfoVO {
    Integer accountId;
    String email;
    String username;
    String nickname;
    String image;

    public GuardianInfoVO() {
        this.accountId = null;
        this.email = null;
        this.username = null;
        this.nickname = null;
        this.image = null;
    }
}
