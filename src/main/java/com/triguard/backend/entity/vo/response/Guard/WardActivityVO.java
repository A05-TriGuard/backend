package com.triguard.backend.entity.vo.response.Guard;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class WardActivityVO {
    WardInfo wardInfo;
    List<WardActivityInfoVO> activities;

    @Data
    @AllArgsConstructor
    public static class WardInfo {
        Integer id;
        String email;
        String username;
        String nickname;
        String image;
    }
}
