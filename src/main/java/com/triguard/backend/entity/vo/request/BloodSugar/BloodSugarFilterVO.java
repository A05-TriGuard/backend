package com.triguard.backend.entity.vo.request.BloodSugar;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class BloodSugarFilterVO {
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$")
    String startDate;
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$")
    String endDate;
    @Pattern(regexp = "^\\d{2}:\\d{2}$")
    String startTime;
    @Pattern(regexp = "^\\d{2}:\\d{2}$")
    String endTime;

    @Digits(integer = 2, fraction = 1)
    Float minBs;
    @Digits(integer = 2, fraction = 1)
    Float maxBs;
    @Length(max = 3, min = 3)
    String meal;
    @Length(max = 3, min = 3)
    String feeling;
    @Length(max = 2, min = 2)
    String remark;

    public BloodSugarFilterVO() {
        this.startDate = "1970-01-01";
        this.endDate = "2100-01-01";
        this.startTime = "00:00";
        this.endTime = "23:59";
        this.minBs = 0.0f;
        this.maxBs = 100.0f;
        this.meal = null;
        this.feeling = null;
        this.remark = null;
    }
}
