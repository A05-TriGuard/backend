package com.triguard.backend.entity.vo.request.BloodPressure;

import jakarta.validation.constraints.*;
import lombok.Data;

/**
 * 用于更新血压信息的表单
 */
@Data
public class BloodPressureUpdateVO {
    Integer id;
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
    @Pattern(regexp = "^[a-zA-Z0-9\\u4e00-\\u9fa5]+$")
    String remark;
}
