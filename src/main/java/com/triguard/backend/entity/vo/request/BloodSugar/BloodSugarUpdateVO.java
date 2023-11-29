package com.triguard.backend.entity.vo.request.BloodSugar;

import jakarta.validation.constraints.*;
import lombok.Data;

/**
 * 用于更新血糖信息的表单
 */
@Data
public class BloodSugarUpdateVO {
    @NotNull
    Integer id;
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
