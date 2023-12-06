package com.triguard.backend.entity.vo.response.Guard;

import lombok.Data;

@Data
public class WardInfoVO {
    Integer accountId;
    String email;
    String username;
    String nickname;
    String image;

    public WardInfoVO() {
        this.accountId = null;
        this.email = null;
        this.username = null;
        this.nickname = null;
        this.image = null;
    }
}
