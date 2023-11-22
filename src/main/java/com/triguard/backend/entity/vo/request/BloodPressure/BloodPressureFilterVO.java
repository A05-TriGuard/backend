package com.triguard.backend.entity.vo.request.BloodPressure;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 用于获取筛选血压信息的表单
 */
@Data
public class BloodPressureFilterVO {
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$")
    String startDate;
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$")
    String endDate;
    @Pattern(regexp = "^\\d{2}:\\d{2}$")
    String startTime;
    @Pattern(regexp = "^\\d{2}:\\d{2}$")
    String endTime;

    @Digits(integer = 3, fraction = 0)
    Integer minSbp;
    @Digits(integer = 3, fraction = 0)
    Integer maxSbp;
    @Digits(integer = 3, fraction = 0)
    Integer minDbp;
    @Digits(integer = 3, fraction = 0)
    Integer maxDbp;
    @Digits(integer = 3, fraction = 0)
    Integer minHeartRate;
    @Digits(integer = 3, fraction = 0)
    Integer maxHeartRate;
    @Length(max = 4, min = 4)
    String arm;
    @Length(max = 4, min = 4)
    String feeling;
    @Length(max = 3, min = 3)
    String remark;
}
