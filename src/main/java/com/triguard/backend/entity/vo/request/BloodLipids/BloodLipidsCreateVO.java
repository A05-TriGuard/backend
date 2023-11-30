package com.triguard.backend.entity.vo.request.BloodLipids;

import com.triguard.backend.entity.BaseData;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class BloodLipidsCreateVO implements BaseData {
    @Digits(integer = 3, fraction = 1)
    Float tc;
    @Digits(integer = 3, fraction = 1)
    Float tg;
    @Digits(integer = 3, fraction = 1)
    Float hdl;
    @Digits(integer = 3, fraction = 1)
    Float ldl;
    @Min(0)@Max(2)
    Integer feeling;
    String remark;
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$")
    String date;
    @Pattern(regexp = "^\\d{2}:\\d{2}$")
    String time;
}
