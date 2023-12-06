package com.triguard.backend.entity.vo.response.Guard;

import lombok.Data;

import java.util.List;

@Data
public class WardActivityVO {
    WardInfo wardInfo;
    List<WardActivityInfoVO> activities;

    @Data
    public static class WardInfo {
        String email;
        String username;
        String nickname;

        public WardInfo(String email, String username, String nickname) {
            this.email = email;
            this.username = username;
            this.nickname = nickname;
        }
    }
}
