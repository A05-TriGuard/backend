package com.triguard.backend.entity.vo.response.Sports;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseInfoVO {
    Integer id;
    String startTime;
    String endTime;
    Integer duration;
    Integer type;
    Integer feelings;
    String remark;
}
