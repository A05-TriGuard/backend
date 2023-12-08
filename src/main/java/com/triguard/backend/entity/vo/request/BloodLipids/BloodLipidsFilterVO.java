package com.triguard.backend.entity.vo.request.BloodLipids;

import com.triguard.backend.entity.BaseData;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 用于获取筛选血脂信息的表单
 */
@Data
public class BloodLipidsFilterVO implements BaseData {
    Integer accountId;
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$")
    String startDate;
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$")
    String endDate;
    @Pattern(regexp = "^\\d{2}:\\d{2}$")
    String startTime;
    @Pattern(regexp = "^\\d{2}:\\d{2}$")
    String endTime;

    @Digits(integer = 3, fraction = 1)
    Float minTc;
    @Digits(integer = 3, fraction = 1)
    Float maxTc;
    @Digits(integer = 3, fraction = 1)
    Float minTg;
    @Digits(integer = 3, fraction = 1)
    Float maxTg;
    @Digits(integer = 3, fraction = 1)
    Float minHdl;
    @Digits(integer = 3, fraction = 1)
    Float maxHdl;
    @Digits(integer = 3, fraction = 1)
    Float minLdl;
    @Digits(integer = 3, fraction = 1)
    Float maxLdl;

    @Length(max = 3, min = 3)
    String feeling;
    @Length(max = 2, min = 2)
    String remark;

    public BloodLipidsFilterVO() {
        this.startDate = "1970-01-01";
        this.endDate = "2100-01-01";
        this.startTime = "00:00";
        this.endTime = "23:59";
        this.minTc = 0.0f;
        this.maxTc = 999.9f;
        this.minTg = 0.0f;
        this.maxTg = 999.9f;
        this.minHdl = 0.0f;
        this.maxHdl = 999.9f;
        this.minLdl = 0.0f;
        this.maxLdl = 999.9f;
        this.feeling = null;
        this.remark = null;
    }
}
