package com.triguard.backend.entity.vo.response.Sports;

import lombok.Data;

@Data
public class ExerciseInfoVO {
    Integer id;
    String startTime;
    String endTime;
    Integer duration;
    Integer type;
    Integer feelings;
    String remark;
}
