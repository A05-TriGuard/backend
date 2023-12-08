package com.triguard.backend.entity.vo.request.BloodSugar;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * 用于添加血糖信息的表单
 */
@Data
public class BloodSugarCreateVO {
    Integer accountId;
    @Digits(integer = 2, fraction = 1)
    Float bs;
    @Min(0)@Max(2)
    Integer meal;
    @Min(0)@Max(2)
    Integer feeling;
    String remark;
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$")
    String date;
    @Pattern(regexp = "^\\d{2}:\\d{2}$")
    String time;
}
