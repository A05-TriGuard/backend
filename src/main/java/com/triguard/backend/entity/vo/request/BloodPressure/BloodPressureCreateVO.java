package com.triguard.backend.entity.vo.request.BloodPressure;

import jakarta.validation.constraints.*;
import lombok.Data;

/**
 * 用于添加血压信息的表单
 */
@Data
public class BloodPressureCreateVO {
    Integer accountId;
    @Digits(integer = 3, fraction = 0)
    Integer sbp;
    @Digits(integer = 3, fraction = 0)
    Integer dbp;
    @Digits(integer = 3, fraction = 0)
    Integer heartRate;
    @Min(0)@Max(2)
    Integer arm;
    @Min(0)@Max(2)
    Integer feeling;
    String remark;
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$")
    String date;
    @Pattern(regexp = "^\\d{2}:\\d{2}$")
    String time;
}
