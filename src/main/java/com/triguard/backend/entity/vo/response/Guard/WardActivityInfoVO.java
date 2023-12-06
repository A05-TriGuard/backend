package com.triguard.backend.entity.vo.response.Guard;

import lombok.Data;

@Data
public class WardActivityInfoVO {
    String time;
    Integer type;

    public WardActivityInfoVO(String time, int i) {
        this.time = time;
        this.type = i;
    }
}
