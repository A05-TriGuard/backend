package com.triguard.backend.entity.vo.response.Sports;

import lombok.Data;

@Data
public class ExerciseInfoVO {
    String startTime;
    String endTime;
    Integer duration;
    Integer type;
    Integer feelings;
    String remark;
}
