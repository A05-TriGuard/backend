package com.triguard.backend.entity.vo.request.Sports;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class ExerciseFilterVO {
    Integer accountId;

    @NotNull
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$")
    String startDate;
    @NotNull
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$")
    String endDate;
    @Pattern(regexp = "^\\d{2}:\\d{2}$")
    String startTime = "00:00";
    @Pattern(regexp = "^\\d{2}:\\d{2}$")
    String endTime = "23:59";
    @Min(0)@Max(100000)
    Integer minDuration = 0;
    @Min(0)@Max(100000)
    Integer maxDuration = 100000;
    @Length(max = 3, min = 3)
    String feelings = null;
    @Length(max = 2, min = 2)
    String remark = null;
}
