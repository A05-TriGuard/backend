package com.triguard.backend.entity.vo.response.Sports;

import lombok.Data;

@Data
public class CurrentExerciseVO {
    Boolean isExercising;
    Boolean isPausing;
    String startTime;
    Integer type;
}
